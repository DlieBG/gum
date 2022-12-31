package de.benediktschwering.gum.cli.utils;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Component
public class GumUtils {
    public Path findGum() {
        try {
            var path = Paths.get(System.getProperty("user.dir"));
            do {
                var gum = Paths.get(path.toString(), ".gum");
                if (Files.exists(gum)) {
                    return gum;
                }
                path = Paths.get(path.toString(), "..").toAbsolutePath().normalize();
            } while(path.getNameCount()!=0);
        } catch (Exception e) {

        }
        return null;
    }

    public String getFile(String[] args) {
        int i = 0;
        for(;i < args.length; i++) {
            if (args[i].equals("-f")) {
                break;
            }
        }
        if (i == args.length - 1) {
            return null;
        }
        return args[i + 1];
    }
}
