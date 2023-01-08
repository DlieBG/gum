package de.benediktschwering.gum.cli.commands;
import de.benediktschwering.gum.cli.dto.CreateLockDto;
import de.benediktschwering.gum.cli.utils.Api;
import de.benediktschwering.gum.cli.utils.GumUtils;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(name = "lock")
@Component
public class Lock implements Runnable {
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
            var lock = Api.createLock(gumConfig.getRemote(), new CreateLockDto(null,  tag, gumConfig.getUser()));
            if (lock == null) {
                System.out.println("Could not create lock, has somebody else a lock?");
            }
        }
        if (file != null) {
            var lock = Api.createLock(gumConfig.getRemote(), new CreateLockDto(file,  null, gumConfig.getUser()));
            if (lock == null) {
                System.out.println("Could not create lock, has somebody else a lock?");
            }
        }
    }
}
