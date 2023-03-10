package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.dto.CreateLockDto;
import de.benediktschwering.gum.cli.dto.CreateTagVersionDto;
import de.benediktschwering.gum.cli.dto.FileVersionDto;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "tag")
@Component
public class Tag implements Runnable {
    @CommandLine.Option(names = {"-t", "--tag"}, required = true)
    String tag;
    @CommandLine.Option(names = {"-h", "--hard"})
    boolean hard;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        var tagVersion = Api.createTagVersion(gumConfig.getRemote(), new CreateTagVersionDto(tag, gumConfig.getUser(), hard, gumConfig.getLocalFileVersions().stream().map(FileVersionDto::getId).toList()));
        if (tagVersion == null) {
            System.out.println("Could not create tag version, has somebody else a lock?");
            return;
        }
        gumConfig.setBaseTagVersion(tagVersion);
        GumUtils.writeGumConfig(gumConfig);
        if (hard) {
            var createLock = new CreateLockDto("gowl");
            createLock.setTagNameRegex(tag);
            var lock = Api.createLock(gumConfig.getRemote(), createLock);
        }
        System.out.println("Created TagVersion " + tagVersion.getTagName() + " : " + tagVersion.getId());
    }
}
