package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;
import java.util.Collections;

@CommandLine.Command(name = "history")
@Component
public class History implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"})
    String file;
    @CommandLine.Option(names = {"-t", "--tag"})
    String tag;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        if ((tag == null && file == null) || (tag != null && file != null)) {
            System.out.println("Only tag OR file is allowed.");
            return;
        }
        if (tag != null) {
            var tagVersions = Api.getTagVersions(gumConfig.getRemote(), tag);
            if (tagVersions == null) {
                System.out.println("Tag was not found.");
                return;
            }
            for (var tagVersion : tagVersions) {
                System.out.print(tagVersion.getId() + " by " + tagVersion.getUser());
                if (tagVersion.getId().equals(gumConfig.getBaseTagVersion().getId())) {
                    System.out.print(" (base)");
                }
                System.out.println();
            }
        }
        if (file != null) {
            var fileToHist = Paths.get(file);
            var relativeFileName = gumConfig.getRepositoryPath().relativize(fileToHist.toAbsolutePath().normalize());
            var currentLocal = gumConfig.getLocalFileVersions().stream().filter(file -> Paths.get(file.getFileName()).equals(relativeFileName)).findFirst();
            var baseVersion = gumConfig.getBaseTagVersion().getFileVersions().stream().filter(file -> Paths.get(file.getFileName()).equals(relativeFileName)).findFirst();
            var fileVersions = Api.getFileVersions(gumConfig.getRemote(), relativeFileName.toString());
            if (fileVersions == null) {
                System.out.println("File was not found.");
                return;
            }
            for (var fileVersion : fileVersions) {
                System.out.print(fileVersion.getId() + " by " + fileVersion.getUser());
                if (fileVersion.isDeleted()) {
                    System.out.print(" (deleted)");
                }
                if (currentLocal.isPresent() && fileVersion.getId().equals(currentLocal.get().getId())) {
                    System.out.print(" (local)");
                }
                if (baseVersion.isPresent() && fileVersion.getId().equals(baseVersion.get().getId())) {
                    System.out.print(" (base)");
                }
                System.out.println();
            }
        }
    }
}
