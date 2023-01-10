package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.Lock;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteLockDto {
    private String user;
}
