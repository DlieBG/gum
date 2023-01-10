package de.benediktschwering.gum.server.dto;

import de.benediktschwering.gum.server.model.Lock;
import lombok.Getter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Getter
public class LockDto {

    private String id;

    private RepositoryDto repository;

    private String fileNameRegex;

    private String tagNameRegex;

    private String user;

    public LockDto(
            Lock lock
    ) {
        id = lock.getId();
        repository = new RepositoryDto(lock.getRepository());
        fileNameRegex = lock.getFileNameRegex();
        tagNameRegex = lock.getTagNameRegex();
        user = lock.getUser();
    }

}
