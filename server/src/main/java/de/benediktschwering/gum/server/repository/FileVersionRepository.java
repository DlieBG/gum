package de.benediktschwering.gum.server.repository;

import de.benediktschwering.gum.server.model.FileVersion;
import de.benediktschwering.gum.server.model.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FileVersionRepository extends MongoRepository<FileVersion, String> {
    List<FileVersion> searchFileVersionsByRepositoryAndFileNameOrderByIdDesc(Repository repository, String fileName);
}
