package de.benediktschwering.gum.server.controller;

import de.benediktschwering.gum.server.dto.CreateLockDto;
import de.benediktschwering.gum.server.dto.LockDto;
import de.benediktschwering.gum.server.model.Lock;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.repository.LockRepository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class LockController {

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Autowired
    private LockRepository lockRepository;

    @GetMapping("")
    public List<LockDto> getLocks(
            @RequestParam("repositoryId") String repositoryId
    ) {
        Repository repository = repositoryRepository
                .findById(repositoryId)
                .orElseThrow(GumUtils::NotFound);

        return lockRepository
                .searchLocksByRepositoryOrderByIdAsc(repository)
                .stream()
                .map(
                        (Lock lock) ->
                                new LockDto(
                                        lock
                                )
                )
                .toList();
    }

    @GetMapping("/{id}")
    public LockDto getLock(
            @PathVariable("id") String id
    ) {
        return new LockDto(
                lockRepository
                        .findById(id)
                        .orElseThrow(GumUtils::NotFound)
        );
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public LockDto createLock(
            @RequestBody CreateLockDto createLockDto
    ) {
        return new LockDto(
                lockRepository.save(
                        createLockDto.toLock(
                                repositoryRepository
                        )
                )
        );
    }

}
