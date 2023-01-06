package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "delete")
@Component
public class Delete implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"}, required = true)
    String file;

    public void run() {
        GumUtils.getGumConfigOrExit();
    }
}
