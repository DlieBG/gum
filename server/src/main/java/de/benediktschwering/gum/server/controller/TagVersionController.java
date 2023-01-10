package de.benediktschwering.gum.server.controller;

import de.benediktschwering.gum.server.dto.CreateTagVersionDto;
import de.benediktschwering.gum.server.dto.TagVersionDto;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.model.TagVersion;
import de.benediktschwering.gum.server.repository.FileVersionRepository;
import de.benediktschwering.gum.server.repository.LockRepository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.repository.TagVersionRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{repositoryName}/tagversion")
public class TagVersionController {
    @Autowired
    private RepositoryRepository repositoryRepository;
    @Autowired
    private FileVersionRepository fileVersionRepository;
    @Autowired
    private TagVersionRepository tagVersionRepository;
    @Autowired
    private LockRepository lockRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @GetMapping("")
    public List<TagVersionDto> getTagVersions(
            @PathVariable("repositoryName") String repositoryName,
            @RequestParam("tagName") String tagName
    ) {
        Repository repository = repositoryRepository
                .searchRepositoryByName(repositoryName)
                .orElseThrow(GumUtils::NotFound);

        return tagVersionRepository
                .searchTagVersionsByRepositoryAndTagNameOrderByIdDesc(
                        repository,
                        tagName
                )
                .stream()
                .map(
                        (TagVersion tagVersion) ->
                                new TagVersionDto(
                                        tagVersion,
                                        gridFsTemplate
                                )
                )
                .toList();
    }

    @GetMapping("/{id}")
    public TagVersionDto getTagVersion(
            @PathVariable("id") String id
    ) {
        return new TagVersionDto(
                tagVersionRepository
                        .findById(id)
                        .orElseThrow(GumUtils::NotFound),
                gridFsTemplate
        );
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TagVersionDto createTagVersion(
            @PathVariable("repositoryName") String repositoryName,
            @RequestBody CreateTagVersionDto createTagVersionDto
    ) {
        Repository repository = repositoryRepository
                .searchRepositoryByName(repositoryName)
                .orElseThrow(GumUtils::NotFound);
        var locks = lockRepository.searchLocksByRepositoryOrderByIdDesc(repository);
        if (locks != null && locks.stream().anyMatch(lock -> lock.getTagNameRegex() != null && createTagVersionDto.getTagName().startsWith(lock.getTagNameRegex()) && !lock.getUser().equals(createTagVersionDto.getUser()))) {
            throw GumUtils.Conflict();
        }
        return new TagVersionDto(
                tagVersionRepository.save(
                        createTagVersionDto.toTagVersion(
                                repositoryName,
                                repositoryRepository,
                                fileVersionRepository
                        )
                ),
                gridFsTemplate
        );
    }

}
