package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.model.TagVersion;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RepositoryDto {
    private String id;
    private String name;
    private List<TagVersionDto> tagVersions;
    public RepositoryDto(
            Repository repository,
            GridFsTemplate gridFsTemplate
    ) {
        id = repository.getId();
        name = repository.getName();
        tagVersions = repository.getTagVersions().stream().map((tagVersion) -> new TagVersionDto(tagVersion, gridFsTemplate)).toList();
    }

}
