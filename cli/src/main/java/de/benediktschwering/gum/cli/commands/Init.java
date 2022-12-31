package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class Init {
    @Resource
    private GumUtils gumUtils;
    public void run(String[] args) {
        var gumPath = gumUtils.findGum();
        if (gumPath != null) {
            System.out.println(String.format("found existing .gum folder at '%s' sub gum repositories are not supported!", gumPath));
            System.exit(0);
        }
    }
}
