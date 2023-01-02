package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.Repository;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RepositoryDto {

    private String id;

    private String name;

    private List<String> tagnames;


    public RepositoryDto(
            Repository repository
    ) {
        id = repository.getId();
        name = repository.getName();
        tagnames = new ArrayList<String>();

        repository.getTagVersions().forEach(
                (tagVersion) -> {
                    if (!tagnames.contains(tagVersion.getTagname()))
                        tagnames.add(tagVersion.getTagname());
                }
        );
    }

}
