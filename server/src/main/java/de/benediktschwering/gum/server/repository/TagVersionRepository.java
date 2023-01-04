package de.benediktschwering.gum.server.repository;

import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.model.TagVersion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TagVersionRepository extends MongoRepository<TagVersion, String> {

    public List<TagVersion> searchTagVersionsByRepositoryAndTagnameOrderByIdAsc(Repository repository, String tagname);

}
