package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.dto.CreateFileVersionDto;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;

@CommandLine.Command(name = "delete")
@Component
public class Delete implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"}, required = true)
    String file;

    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        var fileToDelete = Paths.get(file);
        if (!fileToDelete.toFile().exists() || fileToDelete.toFile().isDirectory()) {
            System.out.println("File doesn't exist or is a directory!");
            return;
        }
        var relativeFileName = gumConfig.getRepositoryPath().relativize(fileToDelete.toAbsolutePath().normalize());
        if (relativeFileName.toString().contains("..")) {
            System.out.println("File is outside of repository!");
            return;
        }
        var previousLocal = gumConfig.getLocalFileVersions().stream().filter(file -> Paths.get(file.getFileName()).equals(relativeFileName)).findFirst();
        if (previousLocal.isEmpty()) {
            System.out.println("File is not in repository!");
            return;
        }
        var fileVersion = Api.createFileVersion(gumConfig.getRemote(), new CreateFileVersionDto(file, gumConfig.getUser()));
        var deletionResult = fileToDelete.toFile().delete();
        if (!deletionResult) {
            System.out.println("Could not delete file!");
            return;
        }
        gumConfig.getLocalFileVersions().remove(previousLocal.get());
        gumConfig.getLocalFileVersions().add(fileVersion);
        GumUtils.writeGumConfig(gumConfig);
    }
}
