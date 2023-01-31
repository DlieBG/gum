package de.benediktschwering.gum.server.controller;

import de.benediktschwering.gum.server.dto.CreateLockDto;
import de.benediktschwering.gum.server.dto.DeleteLockDto;
import de.benediktschwering.gum.server.dto.LockDto;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.repository.LockRepository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("{repositoryName}/lock")
public class LockController {

    Logger logger = Logger.getLogger("app");
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
                .searchLocksByRepositoryOrderByIdDesc(repository)
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
            logger.severe("Lock is not owned by user");
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
        var locks = lockRepository.searchLocksByRepositoryOrderByIdDesc(repository);
        if (locks != null && locks.stream().anyMatch(lock -> lock.getFileNameRegex() != null && createLockDto.getFileNameRegex().startsWith(lock.getFileNameRegex()) && !lock.getUser().equals(createLockDto.getUser()))) {
            logger.severe("lock already exists due to file name");
            throw GumUtils.Conflict();
        }
        if (locks != null && locks.stream().anyMatch(lock -> lock.getTagNameRegex() != null && createLockDto.getTagNameRegex().startsWith(lock.getTagNameRegex()) && !lock.getUser().equals(createLockDto.getUser()))) {
            logger.severe("lock already exists due to tag name");
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
