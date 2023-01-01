package de.benediktschwering.gum.server.repository;

import de.benediktschwering.gum.server.model.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LockRepository extends MongoRepository<Lock, String> {
}
