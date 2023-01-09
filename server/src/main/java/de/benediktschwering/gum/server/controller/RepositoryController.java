package de.benediktschwering.gum.server.controller;

import de.benediktschwering.gum.server.dto.RepositoryDto;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.model.TagVersion;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.repository.TagVersionRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class RepositoryController {
    @Autowired
    private RepositoryRepository repositoryRepository;
    @Autowired
    private TagVersionRepository tagVersionRepository;

    @GetMapping("")
    public List<RepositoryDto> getRepositories() {
        return repositoryRepository
                .findAll()
                .stream()
                .map(RepositoryDto::new)
                .toList();
    }

    @GetMapping("{repositoryName}")
    public RepositoryDto getRepository(
            @PathVariable("repositoryName") String repositoryName
    ) {
        return new RepositoryDto(
                repositoryRepository
                        .searchRepositoryByName(repositoryName)
                        .orElseThrow(GumUtils::NotFound)
        );
    }

    @PostMapping("{repositoryName}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RepositoryDto createRepository(
            @PathVariable("repositoryName") String repositoryName
    ) {
        if (repositoryRepository
                .searchRepositoryByName(
                        repositoryName
                )
                .isPresent()
        )
            throw GumUtils.Conflict();

        Repository newRepository = repositoryRepository.save(
                new Repository(
                        repositoryName
                )
        );

        tagVersionRepository.save(
                new TagVersion(
                        newRepository,
                        "main",
                        "gowl",
                        new ArrayList<>()
                )
        );
        newRepository = repositoryRepository.findById(newRepository.getId()).orElseThrow(GumUtils::NotFound);
        return new RepositoryDto(
                newRepository
        );
    }

}
