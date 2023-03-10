package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.FileVersion;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.model.TagVersion;
import de.benediktschwering.gum.server.repository.FileVersionRepository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTagVersionDto {
    private String tagName;

    private String user;

    private List<String> fileVersionIds;

    public TagVersion toTagVersion(
            String repositoryName,
            RepositoryRepository repositoryRepository,
            FileVersionRepository fileVersionRepository
    ) {
        Repository repository = repositoryRepository
                .searchRepositoryByName(repositoryName)
                .orElseThrow(GumUtils::NotFound);

        List<FileVersion> fileVersions = fileVersionIds
                .stream()
                .map(
                        (fileVersionId) -> fileVersionRepository
                                .findById(fileVersionId)
                                .orElseThrow(GumUtils::NotFound)
                )
                .toList();

        return new TagVersion(
                repository,
                tagName,
                user,
                fileVersions
        );
    }

}
