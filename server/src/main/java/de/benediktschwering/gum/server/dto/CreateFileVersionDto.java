package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.FileVersion;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFileVersionDto {
    private String fileName;
    private String user;
    public FileVersion toFileVersion(
            String repositoryName,
            RepositoryRepository repositoryRepository
    ) {
        Repository repository = repositoryRepository
                .searchRepositoryByName(repositoryName)
                .orElseThrow(GumUtils::NotFound);

        return new FileVersion(
                repository,
                fileName,
                user
        );
    }

    public FileVersion toFileVersion(
            Repository repository
    ) {
        return new FileVersion(
                repository,
                fileName,
                user
        );
    }
}
