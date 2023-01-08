package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Paths;

@CommandLine.Command(name = "info")
@Component
public class Info implements Runnable {
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        var path = Paths.get(gumConfig.getRepositoryPath().toString(), "..").toAbsolutePath().normalize();
        System.out.println(path);
        System.out.println("Remote: " + gumConfig.getRemote());
        System.out.println("User: " + gumConfig.getUser());
    }
}
