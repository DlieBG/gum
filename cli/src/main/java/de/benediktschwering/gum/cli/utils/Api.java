package de.benediktschwering.gum.cli.utils;

import de.benediktschwering.gum.cli.dto.*;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class Api {
    public static RepositoryDto createRepository(String remote) {
        RestTemplate restTemplate = new RestTemplate();
        var repositoryResponse = restTemplate.postForEntity(remote, null, RepositoryDto.class);
        if (repositoryResponse.getStatusCode().equals(HttpStatusCode.valueOf(409))) {
            return getRepository(remote);
        }
        return repositoryResponse.getBody();
    }

    public static RepositoryDto getRepository(String remote) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(remote, RepositoryDto.class);
    }

    public static TagVersionDto createTagVersion(String remote, CreateTagVersionDto createTagVersionDto) {
        RestTemplate restTemplate = new RestTemplate();
        var tagVersionResponse = restTemplate.postForEntity(remote + "/tagversion", createTagVersionDto, TagVersionDto.class);
        if (tagVersionResponse.getStatusCode().equals(HttpStatusCode.valueOf(409))) {
            return null;
        }
        return tagVersionResponse.getBody();
    }

    public static FileVersionDto createFileVersion(String remote, CreateFileVersionDto createFileVersionDto) {
        RestTemplate restTemplate = new RestTemplate();
        var fileVersionResponse = restTemplate.postForEntity(remote + "/fileversion", createFileVersionDto, FileVersionDto.class);
        return fileVersionResponse.getBody();
    }

    public static File getFileVersionFile(String remote, String fileVersionId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.execute(remote + "/fileversion/" + fileVersionId + "/file", HttpMethod.GET, null, clientHttpResponse -> {
            File ret = File.createTempFile("gum", "");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
    }

    public static void createFileVersionFile(String remote, String fileVersionId, File file) {
        RestTemplate restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        var body = new LinkedMultiValueMap<>();
        body.add("file", file);
        var requestEntity = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(remote + "/fileversion/" + fileVersionId + "/file", requestEntity, Object.class);
    }

    public static LockDto createLock(String remote, CreateLockDto createLockDto) {
        RestTemplate restTemplate = new RestTemplate();
        var lockResponse = restTemplate.postForEntity(remote, createLockDto, LockDto.class);
        if (lockResponse.getStatusCode().equals(HttpStatusCode.valueOf(409))) {
            return null;
        }
        return lockResponse.getBody();
    }

    public static void deleteLock(String remote, String lockId) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(remote + "/lock/" + lockId);
    }

    public static List<TagVersionDto> getTagVersions(String remote, String tagName) {
        RestTemplate restTemplate = new RestTemplate();
        var array = restTemplate.getForObject(remote + "/tagversion?tagName=" + tagName, TagVersionDto[].class);
        return array == null ? null : Arrays.asList(array);
    }

    public static List<FileVersionDto> getFileVersions(String remote, String fileName) {
        RestTemplate restTemplate = new RestTemplate();
        var array = restTemplate.getForObject(remote + "/fileversion?fileName=" + fileName, FileVersionDto[].class);
        return array == null ? null : Arrays.asList(array);
    }

    public static TagVersionDto getTagVersion(String remote, String tagVersion) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(remote + "/tagversion/" + tagVersion, TagVersionDto.class); //TODO
    }

    public static FileVersionDto getFileVersion(String remote, String fileVersion) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(remote + "/fileversion?fileName=" + fileVersion, FileVersionDto.class); //TODO
    }
}
