package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.FileInputStream;
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
        System.out.println("Username: " + gumConfig.getUser());
        var baseTag = gumConfig.getBaseTagVersion();
        System.out.println("Local TagVersion base: " + baseTag.getTagName() + " : " + baseTag.getId() + " by " + baseTag.getUser());
        var fileVersions = gumConfig.getLocalFileVersions();
        System.out.println("Local FileVersions:");
        for (var fileVersion : fileVersions) {
            System.out.print("\t" + fileVersion.getFileName() + " : " + fileVersion.getId() + " by " + fileVersion.getUser());
            var baseFileVersion = baseTag.getFileVersions().stream().filter(fv -> fv.getFileName().equals(fileVersion.getFileName())).findFirst();
            if (baseFileVersion.isPresent()) {
                var baseVersion = baseFileVersion.get();
                if (baseVersion.getId().equals(fileVersion.getId())) {
                    System.out.print(" (base)");
                }
                try {
                    var fileStream = new FileInputStream(Paths.get(gumConfig.getRepositoryPath().toString(), fileVersion.getFileName()).toFile());
                    var sha256 = DigestUtils.sha256Hex(fileStream);
                    if (!sha256.equals(fileVersion.getSha256())) {
                        System.out.print(" (changed)");
                    }
                } catch(Exception ignored) {
                    System.out.print(" (?)");
                }
            }
            System.out.println();
        }

        var repository = Api.getRepository(gumConfig.getRemote());
        System.out.println("Remote Tags");
        for (var tag : repository.getTags()) {
            var tagVersions = Api.getTagVersions(gumConfig.getRemote(), tag);
            var tagVersion = tagVersions.get(0);
            System.out.print("\t" + tagVersion.getTagName() + " : " + tagVersion.getId() + " by " + tagVersion.getUser());
            if (tagVersion.getId().equals(gumConfig.getBaseTagVersion().getId())) {
                System.out.print(" (base)");
            }
            System.out.println();
            for (var fileVersion : tagVersion.getFileVersions()) {
                System.out.println("\t\t" + fileVersion.getFileName() + " : " + fileVersion.getId() + " by " + fileVersion.getUser());
            }
        }

        var locks = Api.getLocks(gumConfig.getRemote());
        if (!locks.isEmpty()) {
            System.out.println("Remote Locks:");
            for (var lock: locks) {
                if (lock.getFileNameRegex() != null) {
                    System.out.println("\tfile - "+ lock.getFileNameRegex() + " : " + lock.getId() + " by " + lock.getUser());
                }
                if (lock.getTagNameRegex() != null) {
                    System.out.println("\ttag - "+ lock.getTagNameRegex() + " : " + lock.getId() + " by " + lock.getUser());
                }
            }
        }
    }
}
