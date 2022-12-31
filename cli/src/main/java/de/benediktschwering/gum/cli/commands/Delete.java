package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.GumUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class Delete {
    @Resource
    private GumUtils gumutils;
    public void run(Path gumPath, String[] args) {
        var file = gumutils.getFile(args);
    }
}
