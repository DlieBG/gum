package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "tag")
@Component
public class Tag implements Runnable {
    @CommandLine.Option(names = {"-t", "--tag"}, required = true)
    String tag;
    @CommandLine.Option(names = {"-h", "--hard"}, required = false)
    boolean hard;
    @CommandLine.Option(names = {"-d", "--delete"}, required = false)
    boolean delete;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
    }
}
