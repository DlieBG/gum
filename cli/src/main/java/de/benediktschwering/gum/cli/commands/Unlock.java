package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "unlock")
@Component
public class Unlock implements Runnable {
    @CommandLine.Parameters()
    String lockId;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        Api.deleteLock(gumConfig.getRemote(), lockId);
    }
}
