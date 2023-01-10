package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.Lock;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

@Getter
@Setter
public class CreateLockDto {
    private String fileNameRegex;

    private String tagNameRegex;

    private String user;

    public Lock toLock(
            String repositoryName,
            RepositoryRepository repositoryRepository
    ) {
        Repository repository = repositoryRepository
                .searchRepositoryByName(repositoryName)
                .orElseThrow(GumUtils::NotFound);

        var lock = new Lock(
                repository,
                user
        );
        lock.setFileNameRegex(fileNameRegex);
        lock.setTagNameRegex(tagNameRegex);
        return lock;
    }

}
