package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;

@CommandLine.Command(name = "diff")
@Component
public class Diff implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"}, required = false)
    String file;

    @CommandLine.Parameters()
    String[] versions;

    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        if (versions.length > 2) {
            System.out.println("Only two versions can be diffed against each other.");
            System.exit(0);
        }
        if ((file == null && versions.length == 0) || (file != null && versions.length != 0)) {
            System.out.println("Cou can diff a file OR two versions.");
            System.exit(0);
        }

        if (file != null) { //TODO hash?
            var fileToDiff = Paths.get(file);
            var relativeFileName = gumConfig.getRepositoryPath().relativize(fileToDiff.toAbsolutePath().normalize());
            var previousLocal = gumConfig.getLocalFileVersions().stream().filter(file -> Paths.get(file.getFileName()).equals(relativeFileName)).findFirst();
            if (previousLocal.isEmpty()) {
                System.out.println("File doesn't exist locally!");
                return;
            }
            var fileVersions = Api.getFileVersions(gumConfig.getRemote(), file);
            var fileVersion = fileVersions.get(fileVersions.size() - 1);
            if (previousLocal.get().getId().equals(fileVersion.getId())) {
                System.out.println("Local FileVersion is equal to remote version.");
                return;
            }
            //TODO diff
            return;
        }
        var first = Api.getFileVersionFile(gumConfig.getRemote(), versions[0]);
        if (first == null) {
            System.out.println("FileVersion is not valid.");
            return;
        }
        var second = Api.getFileVersionFile(gumConfig.getRemote(), versions[1]);
        if (second == null) {
            System.out.println("FileVersion is not valid.");
            return;
        }
        //TODO diff
    }
}
