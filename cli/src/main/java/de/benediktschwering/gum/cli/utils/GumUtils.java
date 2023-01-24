package de.benediktschwering.gum.cli.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import de.benediktschwering.gum.cli.dto.FileVersionDto;
import de.benediktschwering.gum.cli.dto.TagVersionDto;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

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

    public static Path getCWD() {
        return Paths.get(System.getProperty("user.dir"));
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
            return new FullGumConfig(Paths.get(gumPath.toString(), "..").toAbsolutePath().normalize(), gumConfig.getRemote(), gumConfig.getUser(), gumConfig.getBaseTagVersion(), gumConfig.getLocalFileVersions());
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
            var config = new GumConfig(fullConfig.getRemote(), fullConfig.getUser(), fullConfig.getBaseTagVersion(), fullConfig.getLocalFileVersions());
            Writer writer = new FileWriter(Paths.get(fullConfig.getRepositoryPath().toString(), ".gum", "config.json").toFile());
            gson.toJson(config, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("Failed to write gum config!");
            System.exit(0);
        }
    }

    public static void setGumToState(FullGumConfig gumConfig, TagVersionDto tagVersionDto) {
        var versionsCopy = gumConfig.getLocalFileVersions().subList(0, gumConfig.getLocalFileVersions().size());
        for (var localFileVersion : versionsCopy) {
            if (tagVersionDto.getFileVersions().stream().noneMatch(tagFileVersion -> tagFileVersion.getFileName().equals(localFileVersion.getFileName()))) {
                localFileVersion.setDeleted(true);
                setFileToState(gumConfig.getRepositoryPath(), gumConfig.getRemote(), localFileVersion);
                gumConfig.getLocalFileVersions().remove(localFileVersion);
                GumUtils.writeGumConfig(gumConfig);
            }
        }
        GumUtils.setDirectoryToState(gumConfig.getRepositoryPath(), gumConfig.getRemote(), tagVersionDto.getFileVersions());
        gumConfig.setBaseTagVersion(tagVersionDto);
        gumConfig.setLocalFileVersions(tagVersionDto.getFileVersions());
        GumUtils.writeGumConfig(gumConfig);
    }

    public static void setDirectoryToState(Path repositoryPath, String remote, List<FileVersionDto> fileVersions) {
        for (var fileVersion : fileVersions) {
            setFileToState(repositoryPath, remote, fileVersion);
        }
    }

    public static void setFileToState(Path repositoryPath, String remote, FileVersionDto fileVersion) {
        if (fileVersion.getFileName().contains("..")) {
            System.out.println("Dangerous file name encountered: '" + fileVersion.getFileName() + "' exiting...");
            System.exit(0);
        }
        if (fileVersion.isDeleted()) {
            try {
                Files.delete(Paths.get(fileVersion.getFileName()));
            } catch (Exception e) {
                System.out.println("Could not set file '" + fileVersion.getFileName() + "' to version.");
            }
            return;
        }
        var file = Api.getFileVersionFile(remote, fileVersion.getId());
        var filePath = Paths.get(repositoryPath.toString(), fileVersion.getFileName());
        Paths.get(filePath.toString(), "..").toFile().mkdirs();
        try {
            Files.copy(file.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Could not set file '" + fileVersion.getFileName() + "' to version.");
        }
    }

    public static void setFileToVersion(FullGumConfig gumConfig, FileVersionDto fileVersion, Optional<FileVersionDto> previousLocal) {
        GumUtils.setFileToState(gumConfig.getRepositoryPath(), gumConfig.getRemote(), fileVersion);
        if (previousLocal.isPresent()) {
            gumConfig.getLocalFileVersions().remove(previousLocal.get());
        }
        if (!fileVersion.isDeleted()) {
            gumConfig.getLocalFileVersions().add(fileVersion);
        }
        GumUtils.writeGumConfig(gumConfig);
    }

    public static String dateFromObjectId(String id) {
        var date = new Date(parseInt(id.substring(0, 8), 16) * 1000L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }
}
