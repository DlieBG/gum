package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Paths;

@CommandLine.Command(name = "diff")
@Component
public class Diff implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"})
    String file;

    @CommandLine.Parameters()
    String[] versions;

    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        if (versions != null && versions.length != 2) {
            System.out.println("Only two versions can be diffed against each other.");
            return;
        }
        if ((file == null && (versions == null || versions.length == 0)) || (file != null && versions != null && versions.length != 0)) {
            System.out.println("Cou can diff a file OR two versions.");
            return;
        }

        if (file != null) {
            var fileToDiff = Paths.get(file);
            var relativeFileName = gumConfig.getRepositoryPath().relativize(fileToDiff.toAbsolutePath().normalize());
            var fileVersions = Api.getFileVersions(gumConfig.getRemote(), relativeFileName.toString());
            if (fileVersions == null) {
                System.out.println("File doesn't exist in remote!");
                return;
            }
            var fileVersion = fileVersions.get(fileVersions.size() - 1);
            if (fileVersion == null || fileVersion.isDeleted()) {
                System.out.println("File doesn't exist anymore in remote!");
                return;
            }
            var diffFile = Api.getFileVersionFile(gumConfig.getRemote(), fileVersion.getId());
            diff("Remote", diffFile, "Your version", fileToDiff.toFile());
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
        diff(versions[0], first, versions[1], second);
    }

    public void diff(String first, File firstFile, String second, File secondFile) {
        try {
            var firstInput = new FileInputStream(firstFile);
            var secondInput = new FileInputStream(firstFile);
            if (DigestUtils.sha256Hex(firstInput).equals(DigestUtils.sha256Hex(secondInput))) {
                System.out.println("Files are equal!");
                return;
            }
        } catch(Exception ignored) {

        }
        System.out.println("### "+ first +" ###");
        System.out.println();
        try {
            var input = new BufferedReader(new FileReader(firstFile));
            String line;
            while( (line = input.readLine()) != null ) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Could not display file...");
        }
        System.out.println();
        System.out.println("### "+ second +" ###");
        System.out.println();
        try {
            var input = new BufferedReader(new FileReader(secondFile));
            String line;
            while( (line = input.readLine()) != null ) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Could not display file...");
        }
    }
}
