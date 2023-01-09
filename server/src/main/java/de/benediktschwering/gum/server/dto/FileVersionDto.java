package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.FileVersion;
import lombok.Getter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.ArrayList;
import java.util.Optional;

@Getter
public class FileVersionDto {

    private String id;

    private String fileName;

    private String user;

    private boolean deleted = true;

    private String md5;

    private String sha256;

    public FileVersionDto(
            FileVersion fileVersion,
            GridFsTemplate gridFsTemplate
    ) {
        id = fileVersion.getId();
        fileName = fileVersion.getFileName();
        user = fileVersion.getUser();

        fileVersion.getFile(gridFsTemplate).ifPresent(
                (file) -> {
                    deleted = false;
                    md5 = file.getMetadata().get("md5").toString();
                    sha256 = file.getMetadata().get("sha256").toString();
                }
        );
    }

}
