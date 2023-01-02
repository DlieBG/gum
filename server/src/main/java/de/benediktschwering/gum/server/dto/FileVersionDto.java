package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.FileVersion;
import lombok.Getter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.ArrayList;
import java.util.Optional;

@Getter
public class FileVersionDto {

    private String id;

    private RepositoryDto repository;

    private String filename;

    private String user;

    private boolean deleted = true;

    private Optional<String> md5 = Optional.empty();

    private Optional<String> sha256 = Optional.empty();

    public FileVersionDto(
            FileVersion fileVersion,
            GridFsTemplate gridFsTemplate
    ) {
        id = fileVersion.getId();
        repository = new RepositoryDto(fileVersion.getRepository());
        filename = fileVersion.getFilename();
        user = fileVersion.getUser();

        fileVersion.getFile(gridFsTemplate).ifPresent(
                (file) -> {
                    deleted = false;
                    md5 = Optional.ofNullable(file.getMetadata().get("md5").toString());
                    sha256 = Optional.ofNullable(file.getMetadata().get("sha256").toString());
                }
        );
    }

}
