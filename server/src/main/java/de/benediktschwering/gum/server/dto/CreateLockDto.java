package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.Lock;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLockDto {

    private String repositoryId;

    private String filenameRegex;

    private String tagnameRegex;

    private String user;

    public Lock toLock(
            RepositoryRepository repositoryRepository
    ) {
        Repository repository = repositoryRepository
                .findById(repositoryId)
                .orElseThrow(GumUtils::NotFound);

        return new Lock(
                repository,
                filenameRegex,
                tagnameRegex,
                user
        );
    }

}
