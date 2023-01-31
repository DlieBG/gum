package de.benediktschwering.gum.server.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

public class LogConfiguration {

    public LogConfiguration() {
        final LogManager logManager = LogManager.getLogManager();
        try {
            logManager.readConfiguration(new FileInputStream("./server/src/main/resources/logging.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] (%2$s) %5$s %6$s%n");

        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINEST);
        consoleHandler.setFormatter(new SimpleFormatter());
        final Logger app = Logger.getLogger("app");
        app.setLevel(Level.FINEST);
        app.addHandler(consoleHandler);
    }
}
