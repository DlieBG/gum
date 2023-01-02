package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.TagVersion;
import lombok.Getter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TagVersionDto {

    private String id;

    private RepositoryDto repository;

    private String tagname;

    private String user;

    private List<FileVersionDto> fileVersions;

    public TagVersionDto(
            TagVersion tagVersion,
            GridFsTemplate gridFsTemplate
    ) {
        id = tagVersion.getId();
        repository = new RepositoryDto(tagVersion.getRepository());
        tagname = tagVersion.getTagname();
        user = tagVersion.getUser();
        fileVersions = new ArrayList<FileVersionDto>();

        tagVersion.getFileVersions().forEach(
                (fileVersion) -> {
                    fileVersions.add(new FileVersionDto(fileVersion, gridFsTemplate));
                }
        );
    }

}
