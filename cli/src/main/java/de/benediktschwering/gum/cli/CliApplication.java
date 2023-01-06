package de.benediktschwering.gum.cli;

import de.benediktschwering.gum.cli.commands.*;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class CliApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CliApplication.class, args);
	}

	@Resource
	CliCommand cliCommand;

	@Override
	public void run(String... args) throws Exception {
		CommandLine.run(cliCommand, args);
	}
}

