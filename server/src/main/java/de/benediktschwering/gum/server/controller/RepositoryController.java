package de.benediktschwering.gum.server.controller;

import de.benediktschwering.gum.server.dto.CreateRepositoryDto;
import de.benediktschwering.gum.server.dto.RepositoryDto;
import de.benediktschwering.gum.server.model.FileVersion;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.model.TagVersion;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.repository.TagVersionRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/repository")
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

    @GetMapping("/{id}")
    public RepositoryDto getRepository(
            @PathVariable("id") String id
    ) throws ResponseStatusException {
        return new RepositoryDto(
                repositoryRepository
                        .findById(id)
                        .orElseThrow(GumUtils::NotFound)
        );
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createRepository(
            @RequestBody CreateRepositoryDto createRepositoryDto
    ) throws ResponseStatusException {
        if (repositoryRepository
                .searchRepositoryByName(
                        createRepositoryDto.getName()
                )
                .isPresent()
        )
            throw GumUtils.Conflict();

        Repository newRepository = repositoryRepository.save(
                createRepositoryDto.toRepository()
        );

        tagVersionRepository.save(
                new TagVersion(
                        newRepository,
                        "main",
                        "gowl",
                        new ArrayList<FileVersion>()
                )
        );
    }

}
