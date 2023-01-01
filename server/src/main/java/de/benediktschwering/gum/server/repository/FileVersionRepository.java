package de.benediktschwering.gum.server.repository;

import de.benediktschwering.gum.server.model.FileVersion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileVersionRepository extends MongoRepository<FileVersion, String> {
}
