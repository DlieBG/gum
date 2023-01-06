package de.benediktschwering.gum.cli.commands;
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
        GumUtils.getGumConfigOrExit();
    }
}
