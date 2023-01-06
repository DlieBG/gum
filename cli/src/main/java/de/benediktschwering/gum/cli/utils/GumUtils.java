package de.benediktschwering.gum.cli.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GumUtils {
    public static Path findGumPath() {
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

    public static FullGumConfig getGumConfigOrExit() {
        try {
            var gumPath = findGumPath();
            if (gumPath == null) {
                System.out.println("Not in gum environment!");
                System.exit(0);
            }
            var configPath = Paths.get(gumPath.toString(), "config.json");
            if (!Files.exists(configPath)) {
                System.out.println("Not in configured gum environment!");
                System.exit(0);
            }
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            JsonReader reader = new JsonReader(new FileReader(configPath.toFile()));
            var gumConfig =  (GumConfig) gson.fromJson(reader, GumConfig.class);
            return new FullGumConfig(configPath, gumConfig.getRemote(), gumConfig.getUser(), gumConfig.getCurrentTagVersion());
        } catch (Exception e) {
            System.out.println("Failed to read gum config!");
            System.exit(0);
        }
        return null;
    }

    public static void writeGumConfig(FullGumConfig fullConfig) {
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            var config = new GumConfig(fullConfig.getRemote(), fullConfig.getUser(), fullConfig.getCurrentTagVersion());
            Writer writer = new FileWriter(fullConfig.getPath().toFile());
            gson.toJson(config, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("Failed to write gum config!");
            System.exit(0);
        }
    }
}
