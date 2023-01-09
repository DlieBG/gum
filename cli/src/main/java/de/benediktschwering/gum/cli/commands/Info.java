package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.utils.Api;
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
        System.out.println("Username: " + gumConfig.getUser());
        var baseTag = gumConfig.getBaseTagVersion();
        System.out.println("Local TagVersion base: " + baseTag.getTagName() + " : " + baseTag.getId() + " by " + baseTag.getUser());
        var fileVersions = gumConfig.getLocalFileVersions();
        System.out.println("Local FileVersions:");
        for (var fileVersion : fileVersions) {
            System.out.print("\t" + fileVersion.getFileName() + " : " + fileVersion.getId() + " by " + fileVersion.getUser());
            var baseFileVersion = baseTag.getFileVersions().stream().filter(fv -> fv.getFileName().equals(fileVersion.getFileName())).findFirst();
            if (baseFileVersion.isPresent()) {
                var localVersion = baseFileVersion.get();
                if (fileVersion.isDeleted()) {
                    System.out.print(" (deleted)");
                }
                if (localVersion.getId().equals(fileVersion.getId())) {
                    System.out.print(" (base)");
                }
                //TODO show if hash is different
            }
            System.out.println();
        }

        var repository = Api.getRepository(gumConfig.getRemote());
        System.out.println("Remote Tags");
        for (var tag : repository.getTags()) {
            var tagVersions = Api.getTagVersions(gumConfig.getRemote(), tag);
            var tagVersion = tagVersions.get(tagVersions.size() - 1);
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
        for (var lock: locks) {
            if (lock.getFileNameRegex() != null) {
                System.out.println("Lock: file - "+ lock.getFileNameRegex() + " : " + lock.getId() + " by " + lock.getUser());
            }
            if (lock.getTagNameRegex() != null) {
                System.out.println("Lock: tag - "+ lock.getTagNameRegex() + " : " + lock.getId() + " by " + lock.getUser());
            }
        }
    }
}
