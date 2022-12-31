package de.benediktschwering.gum.cli;

import de.benediktschwering.gum.cli.commands.*;
import de.benediktschwering.gum.cli.utils.GumUtils;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@SpringBootApplication
public class CliApplication implements CommandLineRunner {

	@Resource
	private Delete delete;
	@Resource
	private Diff diff;
	@Resource
	private History history;
	@Resource
	private Info info;
	@Resource
	private Init init;
	@Resource
	private Lock lock;
	@Resource
	private Recall recall;
	@Resource
	private Sync sync;
	@Resource
	private Tag tag;
	@Resource
	private Unlock unlock;
	@Resource
	private Update update;
	@Resource
	private GumUtils gumutils;

	public static void main(String[] args) {
		SpringApplication.run(CliApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length < 1) {
			printHelp();
			return;
		}

		var splicedArgs = spliceFirst(args);
		switch (args[0]) {
			case "-h":
			case "--help":
				printHelp();
				return;
			case "init":
				init.run(splicedArgs);
				return;
		}
		var gumPath = gumutils.findGum();
		if (gumPath == null) {
			System.out.println(".gum folder not found exiting...");
			System.exit(0);
		}
		System.out.println(gumPath);
		switch (args[0]) {
			case "delete":
				delete.run(gumPath, splicedArgs);
				break;
			case "diff":
				diff.run(gumPath, splicedArgs);
				break;
			case "history":
				history.run(gumPath, splicedArgs);
				break;
			case "info":
				info.run(gumPath, splicedArgs);
				break;
			case "lock":
				lock.run(gumPath, splicedArgs);
				break;
			case "recall":
				recall.run(gumPath, splicedArgs);
				break;
			case "sync":
				sync.run(gumPath, splicedArgs);
				break;
			case "tag":
				tag.run(gumPath, splicedArgs);
				break;
			case "unlock":
				unlock.run(gumPath, splicedArgs);
				break;
			case "update":
				update.run(gumPath, splicedArgs);
				break;
		}
	}

	private void printHelp() {

	}

	public static String[] spliceFirst(String[] array)
	{
		return Arrays.copyOfRange(array, 1, array.length);
	}
}
