package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.dto.TagVersionDto;
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
        var gumPath = GumUtils.findGumPath();
        if (gumPath != null) {
            System.out.printf("found existing .gum folder at '%s' sub gum repositories are not supported!%n", gumPath);
            System.exit(0);
        }
        var path = Paths.get(System.getProperty("user.dir"));
        var files = path.toFile().list();
        if ((files == null || files.length > 0) && !force) {
            System.out.println("Directory not empty!");
            System.exit(0);
        }
        var configDirectory = Paths.get(path.toString(), ".gum");
        if (!configDirectory.toFile().mkdirs()) {
            System.out.println("Could not create directory!");
            System.exit(0);
        }
        var configPath = Paths.get(configDirectory.toString(), "config.json");
        var fullGumConfig = new FullGumConfig(configPath, remote, System.getProperty("user.name"), new TagVersionDto());
        GumUtils.writeGumConfig(fullGumConfig);
    }
}
