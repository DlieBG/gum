package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.dto.CreateFileVersionDto;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.FullGumConfig;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Scanner;

@CommandLine.Command(name = "update")
@Component
public class Update implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"})
    String file;
    @CommandLine.Option(names = {"-y", "--yes"})
    boolean yes;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        if (file != null) {
            updateFile(gumConfig, file);
            return;
        }
        updateAllFiles(gumConfig, gumConfig.getRepositoryPath().toFile());
    }

    private void updateAllFiles(FullGumConfig gumConfig, File directory) {
        var files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (var file : files) {
            if (file.toString().contains(".gum")) {
                continue;
            }
            if (file.isDirectory()) {
                updateAllFiles(gumConfig, file);
                continue;
            }
            updateFile(gumConfig, file.toString());
        }
    }

    private void updateFile(FullGumConfig gumConfig, String file) {
        if (file.contains(".gum")) {
            System.out.println("A fileName containing .gum is not valid!");
            return;
        }
        var fileToUpdate = Paths.get(file);
        if (!fileToUpdate.toFile().exists()) {
            System.out.println("File doesn't exist!");
            return;
        }
        if (fileToUpdate.toFile().isDirectory()) {
            System.out.println("File is a directory!");
            return;
        }
        var relativeFileName = gumConfig.getRepositoryPath().relativize(fileToUpdate.toAbsolutePath().normalize());
        if (relativeFileName.toString().contains("..")) {
            System.out.println("File is outside of repository!");
            return;
        }
        try {
            var previousLocal = gumConfig.getLocalFileVersions().stream().filter(localFile -> Paths.get(localFile.getFileName()).equals(relativeFileName)).findFirst();
            var fileStream = new FileInputStream(fileToUpdate.toFile());
            var sha256 = DigestUtils.sha256Hex(fileStream);
            if (previousLocal.isPresent() && sha256.equals(previousLocal.get().getSha256())) {
                System.out.println("No changes in: " + relativeFileName);
                return;
            }
            if (!yes) {
                Scanner userInput = new Scanner(System.in);
                System.out.println("Add file '" + relativeFileName + "' ? (y/yes/n/no)");
                var input = userInput.nextLine().trim().toLowerCase();
                if (!input.equals("y") && !input.equals("yes")) {
                    System.out.println("Skipping: " + relativeFileName);
                    return;
                }
            }
            var fileVersion = Api.createFileVersion(gumConfig.getRemote(), new CreateFileVersionDto(relativeFileName.toString(), gumConfig.getUser()));
            if (fileVersion == null) {
                System.out.println("Could not update file, because someone had a lock in place: " + relativeFileName);
                System.out.println("Moving to the next...");
                return;
            }
            fileVersion = Api.createFileVersionFile(gumConfig.getRemote(), fileVersion.getId(), fileToUpdate.toFile());
            GumUtils.setFileToVersion(gumConfig, fileVersion, previousLocal);
            System.out.println("Updated: '" + relativeFileName + "' to '" + fileVersion.getId() + "'.");
        }
        catch (Exception e) {
            System.out.println("Could not update file: " + relativeFileName);
            System.out.println("Moving to the next...");
        }
    }
}
