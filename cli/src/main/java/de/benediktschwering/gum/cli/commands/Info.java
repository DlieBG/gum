package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "info")
@Component
public class Info implements Runnable {
    @Override
    public void run() {
        GumUtils.getGumConfigOrExit();
    }
}
