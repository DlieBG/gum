package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.dto.CreateLockDto;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "lock")
@Component
public class Lock implements Runnable {
    @CommandLine.Option(names = {"-f", "--file"})
    String file;
    @CommandLine.Option(names = {"-t", "--tag"})
    String tag;
    @Override
    public void run() {
        var gumConfig = GumUtils.getGumConfigOrExit();
        if ((tag == null && file == null) || (tag != null && file != null)) {
            System.out.println("Only tag OR file is allowed.");
            return;
        }
        if (tag != null) {
            var createLock = new CreateLockDto(gumConfig.getUser());
            createLock.setTagNameRegex(tag);
            var lock = Api.createLock(gumConfig.getRemote(), createLock);
            if (lock == null) {
                System.out.println("Could not create lock, has somebody else a lock?");
                return;
            }
            System.out.println("Created lock " + lock.getId());
        }
        if (file != null) {
            var createLock = new CreateLockDto(gumConfig.getUser());
            createLock.setFileNameRegex(file);
            var lock = Api.createLock(gumConfig.getRemote(), createLock);
            if (lock == null) {
                System.out.println("Could not create lock, has somebody else a lock?");
                return;
            }
            System.out.println("Created lock " + lock.getId());
        }
    }
}
