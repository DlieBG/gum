package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;

@CommandLine.Command(name = "sync")
@Component
public class Sync implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"})
    String file;
    @CommandLine.Option(names = {"-t", "--tag"})
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
            var fileToUpdate = Paths.get(file);
            var relativeFileName = gumConfig.getRepositoryPath().relativize(fileToUpdate.toAbsolutePath().normalize());
            var previousLocal = gumConfig.getLocalFileVersions().stream().filter(file -> Paths.get(file.getFileName()).equals(relativeFileName)).findFirst();
            var fileVersions = Api.getFileVersions(gumConfig.getRemote(), relativeFileName.toString());
            if (fileVersions.isEmpty()) {
                System.out.println("No file version to recover!");
                return;
            }
            var fileVersion = fileVersions.get(fileVersions.size() - 1);
            GumUtils.setFileToVersion(gumConfig, fileVersion, previousLocal);
            return;
        }
        var tagVersions = Api.getTagVersions(gumConfig.getRemote(), gumConfig.getBaseTagVersion().getTagName());
        var tagVersion = tagVersions.get(tagVersions.size() - 1);
        GumUtils.setGumToState(gumConfig, tagVersion);
    }
}
