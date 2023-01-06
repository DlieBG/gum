package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "diff")
@Component
public class Diff implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"}, required = false)
    String file;

    @CommandLine.Parameters()
    String[] versions;

    @Override
    public void run() {
        GumUtils.getGumConfigOrExit();
        if (versions.length > 2) {
            System.out.println("Only two versions can be diffed against each other.");
            System.exit(0);
        }
        if ((file == null && versions.length == 0) || (file != null && versions.length != 0)) {
            System.out.println("Cou can diff a file OR two versions.");
            System.exit(0);
        }
    }
}
