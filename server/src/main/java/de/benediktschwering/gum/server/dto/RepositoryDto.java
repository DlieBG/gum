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
    private List<String> tags;
    public RepositoryDto(
            Repository repository
    ) {
        id = repository.getId();
        name = repository.getName();
        tags = repository.getTagVersions().stream().map(TagVersion::getTagName).distinct().toList();
    }

}
