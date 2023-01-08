package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.dto.CreateFileVersionDto;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.FullGumConfig;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;

@CommandLine.Command(name = "update")
@Component
public class Update implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"}, required = false)
    String file;
    @CommandLine.Option(names = {"-y", "--yes"}, required = false)
    boolean yes;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        if (file != null) {
            updateFile(gumConfig, file);
            return;
        }
        updateAllFiles(gumConfig);
    }

    private void updateAllFiles(FullGumConfig gumConfig) {
        //TODO
    }

    private void updateFile(FullGumConfig gumConfig, String file) {
        if (file.contains(".gum")) {
            System.out.println("A fileName containing .gum is not valid!");
            return;
        }
        var cwd = GumUtils.getCWD();
        var fileToUpdate = Paths.get(cwd.toString(), file);
        if (!fileToUpdate.toFile().exists() || fileToUpdate.toFile().isDirectory()) {
            System.out.println("File doesn't exist or is a directory!");
            return;
        }
        var relativeFileName = gumConfig.getRepositoryPath().relativize(fileToUpdate);
        if (relativeFileName.toString().contains("..")) {
            System.out.println("File is outside of repository!");
            return;
        }
        //TODO check for changes
        var fileVersion = Api.createFileVersion(gumConfig.getRemote(), new CreateFileVersionDto(relativeFileName.toString(), gumConfig.getUser()));
        //TODO get file
        var previousLocal = gumConfig.getLocalFileVersions().stream().filter(localFile -> Paths.get(localFile.getFileName()).equals(relativeFileName)).findFirst();
        GumUtils.setFileToState(gumConfig.getRepositoryPath(), gumConfig.getRemote(), fileVersion);
        if (previousLocal.isPresent()) {
            gumConfig.getLocalFileVersions().remove(previousLocal.get());
        }
        gumConfig.getLocalFileVersions().add(fileVersion);
        GumUtils.writeGumConfig(gumConfig);
    }
}
