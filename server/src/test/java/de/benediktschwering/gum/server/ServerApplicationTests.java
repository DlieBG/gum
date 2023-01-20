package de.benediktschwering.gum.server;

import de.benediktschwering.gum.server.controller.LockController;
import de.benediktschwering.gum.server.dto.CreateLockDto;
import de.benediktschwering.gum.server.dto.DeleteLockDto;
import de.benediktschwering.gum.server.dto.LockDto;
import de.benediktschwering.gum.server.model.Lock;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.model.TagVersion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import de.benediktschwering.gum.server.repository.LockRepository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ServerApplicationTests {
	@InjectMocks
	LockController lockController;

	@Mock
	RepositoryRepository repositoryRepository;

	@Mock
	LockRepository lockRepository;

	@Test
	public void throwsWhenNoRepoFound() throws Exception {
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			List<LockDto> response = lockController.getLocks("testRepo");
		});

		String expectedMessage = "404 NOT_FOUND";
		String actualMessage = exception.getMessage();

		assertThat(actualMessage.equals(expectedMessage));
	}

	@Test
	public void testGetAllLocks() throws Exception {
		var repo = Optional.of(new Repository("testRepo"));
		repo.get().setTagVersions(new ArrayList<TagVersion>());

		List<Lock> locks = new ArrayList<Lock>(Arrays.asList(new Lock(repo.get(), "test"), new Lock(repo.get(), "test2")));

		when(repositoryRepository.searchRepositoryByName("testRepo")).thenReturn(repo);
		when(lockRepository.searchLocksByRepositoryOrderByIdDesc(repo.get())).thenReturn(locks);

		List<LockDto> response = lockController.getLocks("testRepo");

		assertThat(response.get(0).getUser()).isEqualTo("test");
		assertThat(response.get(0).getRepository().getName()).isEqualTo("testRepo");
	}

	@Test
	public void testGetSingleLock() throws Exception {
		var repo = Optional.of(new Repository("testRepo"));
		repo.get().setTagVersions(new ArrayList<TagVersion>());

		when(lockRepository.findById("id1")).thenReturn(Optional.of(new Lock(repo.get(), "user1")));

		LockDto lockDto = lockController.getLock("id1");
		assertThat(lockDto.getUser()).isEqualTo("user1");
	}

	@Test
	public void testDeleteLock() throws Exception {
		var repo = Optional.of(new Repository("testRepo"));
		repo.get().setTagVersions(new ArrayList<TagVersion>());

		when(lockRepository.findById("id1")).thenReturn(Optional.of(new Lock(repo.get(), "user1")));

		DeleteLockDto deleteLockDto = new DeleteLockDto();
		deleteLockDto.setUser("user1");

		lockController.deleteLock("id1", deleteLockDto);
	}

	@Test
	public void throwsWhenLockConflict() throws Exception {
		var repo = Optional.of(new Repository("testRepo"));
		repo.get().setTagVersions(new ArrayList<TagVersion>());

		when(lockRepository.findById("id1")).thenReturn(Optional.of(new Lock(repo.get(), "user1")));

		DeleteLockDto deleteLockDto = new DeleteLockDto();
		deleteLockDto.setUser("user2");

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			lockController.deleteLock("id1", deleteLockDto);
		});

		String expectedMessage = "409 CONFLICT";
		String actualMessage = exception.getMessage();

		assertThat(actualMessage.equals(expectedMessage));
	}

	@Test
	public void testCreateLock() throws Exception {
		var repo = Optional.of(new Repository("testRepo"));
		repo.get().setTagVersions(new ArrayList<TagVersion>());

		Lock lock1 = new Lock(repo.get(), "user1");
		lock1.setFileNameRegex("testFileRegex");
		lock1.setTagNameRegex("testTagRegex");
		lock1.setId("testId");

		List<Lock> locks = new ArrayList<Lock>(Arrays.asList(lock1, new Lock(repo.get(), "user2")));

		when(repositoryRepository.searchRepositoryByName("testRepo")).thenReturn(repo);
		when(lockRepository.searchLocksByRepositoryOrderByIdDesc(repo.get())).thenReturn(locks);
		when(lockRepository.save(any(Lock.class))).thenReturn(lock1);

		CreateLockDto createLockDto = new CreateLockDto();
		createLockDto.setFileNameRegex("testFileRegexLock");
		createLockDto.setTagNameRegex("testTagRegexLock");
		createLockDto.setUser("user1");

		LockDto lockDto = lockController.createLock("testRepo", createLockDto);
		assertThat(lockDto.getUser()).isEqualTo("user1");
		assertThat(lockDto.getFileNameRegex()).isEqualTo("testFileRegex");
		assertThat(lockDto.getTagNameRegex()).isEqualTo("testTagRegex");
		assertThat(lockDto.getId()).isEqualTo("testId");

	}

	@Test
	public void throwsWhenFileNameRegexMismatch() throws Exception {
		var repo = Optional.of(new Repository("testRepo"));
		repo.get().setTagVersions(new ArrayList<TagVersion>());

		Lock lock1 = new Lock(repo.get(), "user1");
		lock1.setFileNameRegex("testFileName");
		lock1.setTagNameRegex("testTagRegex");
		lock1.setId("testId");

		List<Lock> locks = new ArrayList<Lock>(Arrays.asList(lock1, new Lock(repo.get(), "user2")));

		when(repositoryRepository.searchRepositoryByName("testRepo")).thenReturn(repo);
		when(lockRepository.searchLocksByRepositoryOrderByIdDesc(repo.get())).thenReturn(locks);

		CreateLockDto createLockDto = new CreateLockDto();
		createLockDto.setFileNameRegex("testFileNameRegexLock");
		createLockDto.setTagNameRegex("testTagRegexLock");
		createLockDto.setUser("user2");

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			lockController.createLock("testRepo", createLockDto);
		});

		String expectedMessage = "409 CONFLICT";
		String actualMessage = exception.getMessage();

		assertThat(actualMessage.equals(expectedMessage));
	}

	@Test
	public void throwsWhenTagRegexMismatch() throws Exception {
		var repo = Optional.of(new Repository("testRepo"));
		repo.get().setTagVersions(new ArrayList<TagVersion>());

		Lock lock1 = new Lock(repo.get(), "user1");
		lock1.setFileNameRegex("testFileRegex");
		lock1.setTagNameRegex("testTag");
		lock1.setId("testId");

		List<Lock> locks = new ArrayList<Lock>(Arrays.asList(lock1, new Lock(repo.get(), "user2")));

		when(repositoryRepository.searchRepositoryByName("testRepo")).thenReturn(repo);
		when(lockRepository.searchLocksByRepositoryOrderByIdDesc(repo.get())).thenReturn(locks);

		CreateLockDto createLockDto = new CreateLockDto();
		createLockDto.setFileNameRegex("testFileNameRegexLock");
		createLockDto.setTagNameRegex("testTagRegexLock");
		createLockDto.setUser("user2");

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			lockController.createLock("testRepo", createLockDto);
		});

		String expectedMessage = "409 CONFLICT";
		String actualMessage = exception.getMessage();

		assertThat(actualMessage.equals(expectedMessage));
	}

}
