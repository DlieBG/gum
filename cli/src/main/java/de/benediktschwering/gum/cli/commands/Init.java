package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "init")
@Component
public class Init implements Runnable {
    @CommandLine.Parameters()
    String remote;
    @Override
    public void run() {
        var gumPath = GumUtils.findGumPath();
        if (gumPath != null) {
            System.out.println(String.format("found existing .gum folder at '%s' sub gum repositories are not supported!", gumPath));
            System.exit(0);
        }
    }
}
