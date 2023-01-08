package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.dto.TagVersionDto;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.FullGumConfig;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;

@CommandLine.Command(name = "init")
@Component
public class Init implements Runnable {
    @CommandLine.Parameters()
    String remote;

    @CommandLine.Option(names = {"-F", "--force"}, required = false)
    boolean force;
    @Override
    public void run() {
        if (remote.endsWith("/")) {
            StringBuffer stringBuffer = new StringBuffer(remote);
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            remote = stringBuffer.toString();
        }

        var gumPath = GumUtils.findGumPath();
        if (gumPath != null) {
            System.out.printf("found existing .gum folder at '%s' sub gum repositories are not supported!%n", gumPath);
            return;
        }
        var cwd = GumUtils.getCWD();
        var files = cwd.toFile().list();
        if ((files == null || files.length > 0) && !force) {
            System.out.println("Directory not empty!");
            return;
        }

        var repository = Api.createRepository(remote);
        var baseTagVersion = repository.getTagVersions().stream().filter(tagVersion -> tagVersion.getTagName().equals("main")).findFirst().get();

        var configDirectory = Paths.get(cwd.toString(), ".gum");
        if (!configDirectory.toFile().mkdirs()) {
            System.out.println("Could not create directory!");
            return;
        }
        GumUtils.setDirectoryToState(cwd, remote, baseTagVersion.getFileVersions());
        var fullGumConfig = new FullGumConfig(cwd, remote, System.getProperty("user.name"), baseTagVersion, baseTagVersion.getFileVersions());
        GumUtils.writeGumConfig(fullGumConfig);
    }
}
