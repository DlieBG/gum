package de.benediktschwering.gum.server.repository;

import de.benediktschwering.gum.server.model.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RepositoryRepository extends MongoRepository<Repository, String> {

    Optional<Repository> searchRepositoryByName(String name);

}
