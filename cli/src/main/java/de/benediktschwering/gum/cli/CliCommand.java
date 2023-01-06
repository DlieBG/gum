package de.benediktschwering.gum.cli;

import de.benediktschwering.gum.cli.commands.*;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(subcommands = {Delete.class, Diff.class, History.class, Info.class, Init.class, Lock.class, Recall.class, Sync.class, Tag.class, Unlock.class, Update.class})
@Component
public class CliCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Please specify a command!");
        System.exit(0);
    }
}
