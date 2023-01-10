package de.benediktschwering.gum.server.repository;

import de.benediktschwering.gum.server.model.Lock;
import de.benediktschwering.gum.server.model.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LockRepository extends MongoRepository<Lock, String> {
    List<Lock> searchLocksByRepositoryOrderByIdDesc(Repository repository);
}
