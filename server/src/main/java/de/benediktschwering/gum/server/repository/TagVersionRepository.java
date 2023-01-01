package de.benediktschwering.gum.server.repository;

import de.benediktschwering.gum.server.model.TagVersion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagVersionRepository extends MongoRepository<TagVersion, String> {
}
