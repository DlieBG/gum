package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "recall")
@Component
public class Recall implements Runnable {
    @CommandLine.Parameters()
    String versionId;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        var fileVersion = Api.getFileVersion(gumConfig.getRemote(), versionId);
        if (fileVersion != null) {
            var previousLocal = gumConfig.getLocalFileVersions().stream().filter(file -> file.getFileName().equals(fileVersion.getFileName())).findFirst();
            GumUtils.setFileToState(gumConfig.getRepositoryPath(), gumConfig.getRemote(), fileVersion);
            if (previousLocal.isPresent()) {
                gumConfig.getLocalFileVersions().remove(previousLocal.get());
            }
            gumConfig.getLocalFileVersions().add(fileVersion);
            GumUtils.writeGumConfig(gumConfig);
            return;
        }
        var tagVersion = Api.getTagVersion(gumConfig.getRemote(), versionId);
        if (tagVersion != null) {
            GumUtils.setGumToState(gumConfig, tagVersion);
        }
    }
}
