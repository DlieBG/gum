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

    private String repositoryId;

    private String filename;

    private String user;

    public FileVersion toFileVersion(
            RepositoryRepository repositoryRepository
    ) {
        Repository repository = repositoryRepository
                .findById(repositoryId)
                .orElseThrow(GumUtils::NotFound);

        return new FileVersion(
                repository,
                filename,
                user
        );
    }

}
