package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;

@CommandLine.Command(name = "sync")
@Component
public class Sync implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"}, required = false)
    String file;
    @CommandLine.Option(names = {"-t", "--tag"}, required = false)
    String tag;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        if ((tag != null && file != null)) {
            System.out.println("Tag and file is not allowed.");
            return;
        }

        if (tag != null) {
            var tagVersions = Api.getTagVersions(gumConfig.getRemote(), tag);
            var tagVersion = tagVersions.get(tagVersions.size() - 1);
            GumUtils.setGumToState(gumConfig, tagVersion);
            return;
        }
        if (file != null) {
            var cwd = GumUtils.getCWD();
            var fileToUpdate = Paths.get(cwd.toString(), file);
            var relativeFileName = gumConfig.getRepositoryPath().relativize(fileToUpdate);
            var previousLocal = gumConfig.getLocalFileVersions().stream().filter(file -> Paths.get(file.getFileName()).equals(relativeFileName)).findFirst();
            var fileVersions = Api.getFileVersions(gumConfig.getRemote(), file);
            var fileVersion = fileVersions.get(fileVersions.size() - 1);
            GumUtils.setFileToState(gumConfig.getRepositoryPath(), gumConfig.getRemote(), fileVersion);
            if (previousLocal.isPresent()) {
                gumConfig.getLocalFileVersions().remove(previousLocal.get());
            }
            gumConfig.getLocalFileVersions().add(fileVersion);
            GumUtils.writeGumConfig(gumConfig);
            return;
        }
        var tagVersions = Api.getTagVersions(gumConfig.getRemote(), gumConfig.getBaseTagVersion().getTagName());
        var tagVersion = tagVersions.get(tagVersions.size() - 1);
        GumUtils.setGumToState(gumConfig, tagVersion);
    }
}
