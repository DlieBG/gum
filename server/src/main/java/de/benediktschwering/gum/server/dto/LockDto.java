package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.Lock;
import lombok.Getter;

@Getter
public class LockDto {

    private String id;

    private RepositoryDto repository;

    private String filenameRegex;

    private String tagnameRegex;

    private String user;

    public LockDto(
            Lock lock
    ) {
        id = lock.getId();
        repository = new RepositoryDto(lock.getRepository());
        filenameRegex = lock.getFilenameRegex();
        tagnameRegex = lock.getTagnameRegex();
        user = lock.getUser();
    }

}
