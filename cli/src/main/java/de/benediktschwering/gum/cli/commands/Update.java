package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

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
        if (file.contains(".gum")) {
            System.out.println("A fileName containing .gum is not valid!");
            System.exit(0);
        }
    }
}
