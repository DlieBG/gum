package de.benediktschwering.gum.server.controller;

import de.benediktschwering.gum.server.dto.CreateLockDto;
import de.benediktschwering.gum.server.dto.DeleteLockDto;
import de.benediktschwering.gum.server.dto.LockDto;
import de.benediktschwering.gum.server.model.Lock;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.repository.LockRepository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{repositoryName}/lock")
public class LockController {
    @Autowired
    private RepositoryRepository repositoryRepository;
    @Autowired
    private LockRepository lockRepository;
    @GetMapping("")
    public List<LockDto> getLocks(
            @PathVariable("repositoryName") String repositoryName
    ) {
        Repository repository = repositoryRepository
                .searchRepositoryByName(repositoryName)
                .orElseThrow(GumUtils::NotFound);

        return lockRepository
                .searchLocksByRepositoryOrderByIdAsc(repository)
                .stream()
                .map(
                        LockDto::new
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

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteLock(
            @PathVariable("id") String id,
            @RequestBody DeleteLockDto deleteLockDto
    ) {
        var lock = lockRepository.findById(id).orElseThrow(GumUtils::NotFound);
        if (!lock.getUser().equals(deleteLockDto.getUser())) {
            throw GumUtils.Conflict();
        }
        lockRepository.deleteById(id);
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public LockDto createLock(
            @PathVariable("repositoryName") String repositoryName,
            @RequestBody CreateLockDto createLockDto
    ) {
        Repository repository = repositoryRepository
                .searchRepositoryByName(repositoryName)
                .orElseThrow(GumUtils::NotFound);
        var locks = lockRepository.searchLocksByRepositoryOrderByIdAsc(repository);
        if (locks != null && locks.stream().anyMatch(lock -> createLockDto.getFileNameRegex().startsWith(lock.getFileNameRegex()) && !lock.getUser().equals(createLockDto.getUser()))) {
            throw GumUtils.Conflict();
        }
        if (locks != null && locks.stream().anyMatch(lock -> createLockDto.getTagNameRegex().startsWith(lock.getTagNameRegex()) && !lock.getUser().equals(createLockDto.getUser()))) {
            throw GumUtils.Conflict();
        }
        return new LockDto(
                lockRepository.save(
                        createLockDto.toLock(
                                repositoryName,
                                repositoryRepository
                        )
                )
        );
    }

}
