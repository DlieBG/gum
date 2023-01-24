package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.FullGumConfig;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;
import java.util.Scanner;

@CommandLine.Command(name = "init")
@Component
public class Init implements Runnable {
    @CommandLine.Parameters()
    String remote;

    @CommandLine.Option(names = {"-F", "--force"})
    boolean force;
    @Override
    public void run() {
        try {
            if (remote.endsWith("/")) {
                StringBuffer stringBuffer = new StringBuffer(remote);
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                remote = stringBuffer.toString();
            }

            if (!remote.contains("api")) {
                StringBuffer stringBuffer = new StringBuffer(remote);
                stringBuffer.insert(remote.lastIndexOf("/"), "/api");
                remote = stringBuffer.toString();
            }

            var gumPath = GumUtils.findGumPath();
            if (gumPath != null) {
                System.out.printf("found existing .gum folder at '%s' sub gum repositories are not supported!%n", gumPath);
                return;
            }
            var cwd = GumUtils.getCWD().toAbsolutePath().normalize();
            var files = cwd.toFile().list();
            if ((files == null || files.length > 0) && !force) {
                System.out.println("Directory not empty!");
                return;
            }

            var repository = Api.createRepository(remote);
            var tagVersions = Api.getTagVersions(remote, "main");
            var baseTagVersion = tagVersions.get(0);

            var configDirectory = Paths.get(cwd.toString(), ".gum");
            if (!configDirectory.toFile().mkdirs()) {
                System.out.println("Could not create directory!");
                return;
            }
            GumUtils.setDirectoryToState(cwd, remote, baseTagVersion.getFileVersions());
            var userName = System.getProperty("user.name");
            if (userName.equals("root")) {
                System.out.println("Could you tell us your name?");
                Scanner userInput = new Scanner(System.in);
                do {
                    userName = userInput.nextLine().trim().toLowerCase();
                }
                while (userName.length() == 0);
            }
            var fullGumConfig = new FullGumConfig(cwd, remote, userName, baseTagVersion, baseTagVersion.getFileVersions());
            GumUtils.writeGumConfig(fullGumConfig);
            System.out.println("""
          ___________________________________________
         /                                           |
        /  Hoot! Hoot! You can now start using gum.   |
 /\\ /\\ /_______________________________________________|
((ovo))
():::()
  VVV""");
        } catch (Exception e) {
            System.out.println("Could not init repository, is the remote correct?");
        }
    }
}
