package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.Repository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRepositoryDto {

    private String name;

    public Repository toRepository() {
        return new Repository(
                name
        );
    }

}
