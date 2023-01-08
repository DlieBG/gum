package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "history")
@Component
public class History implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"}, required = false)
    String file;
    @CommandLine.Option(names = {"-t", "--tag"}, required = false)
    String tag;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        if ((tag == null && file == null) || (tag != null && file != null)) {
            System.out.println("Only tag OR file is allowed.");
            return;
        }
        if (tag != null) {
            Api.getTagVersions(gumConfig.getRemote(), tag);
            //TODO
        }
        if (file != null) {
            Api.getFileVersions(gumConfig.getRemote(), file);
            //TODO
        }
    }
}
