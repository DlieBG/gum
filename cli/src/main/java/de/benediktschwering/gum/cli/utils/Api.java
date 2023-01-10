package de.benediktschwering.gum.cli.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.benediktschwering.gum.cli.dto.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class Api {
    public static RepositoryDto createRepository(String remote) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            var repositoryResponse = restTemplate.postForEntity(remote, null, RepositoryDto.class);
            return repositoryResponse.getBody();
        }
        catch (HttpClientErrorException.Conflict e) {
            return getRepository(remote);
        }
    }

    public static RepositoryDto getRepository(String remote) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(remote, RepositoryDto.class);
    }

    public static TagVersionDto createTagVersion(String remote, CreateTagVersionDto createTagVersionDto) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            var tagVersionResponse = restTemplate.postForEntity(remote + "/tagversion", createTagVersionDto, TagVersionDto.class);
            return tagVersionResponse.getBody();
        }
        catch (HttpClientErrorException.Conflict e) {
            return null;
        }
    }

    public static FileVersionDto createFileVersion(String remote, CreateFileVersionDto createFileVersionDto) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            var fileVersionResponse = restTemplate.postForEntity(remote + "/fileversion", createFileVersionDto, FileVersionDto.class);
            return fileVersionResponse.getBody();
        }
            catch (HttpClientErrorException.Conflict e) {
            return null;
        }
    }

    public static File getFileVersionFile(String remote, String fileVersionId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.execute(remote + "/fileversion/" + fileVersionId + "/file", HttpMethod.GET, null, clientHttpResponse -> {
                File ret = File.createTempFile("gum", "");
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                return ret;
            });
        }
        catch (HttpClientErrorException.Conflict e) {
            return null;
        }
    }

    public static FileVersionDto createFileVersionFile(String remote, String fileVersionId, File file) {
        RestTemplate restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        Resource fileResource = new FileSystemResource(file);
        multipartBodyBuilder.part("file", fileResource, MediaType.APPLICATION_OCTET_STREAM);
        MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> httpEntity = new HttpEntity<>(multipartBody, headers);
        return restTemplate.postForEntity(remote + "/fileversion/" + fileVersionId + "/file", httpEntity, FileVersionDto.class).getBody();
    }

    public static LockDto createLock(String remote, CreateLockDto createLockDto) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            var lockResponse = restTemplate.postForEntity(remote + "/lock", createLockDto, LockDto.class);
            return lockResponse.getBody();
        }
        catch (HttpClientErrorException.Conflict e) {
            return null;
        }
    }

    public static List<LockDto> getLocks(String remote) {
        RestTemplate restTemplate = new RestTemplate();
        var array = restTemplate.getForObject(remote + "/lock", LockDto[].class); // TODO encoding
        return array == null ? null : Arrays.asList(array);
    }

    public static boolean deleteLock(String remote, String lockId,  DeleteLockDto deleteLogDto) {
        try {
            Gson gson = new GsonBuilder().create();
            String jsonPayload  = gson.toJson(deleteLogDto);
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(jsonPayload.toString(), headers);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange(remote + "/lock/" + lockId, HttpMethod.DELETE, entity, Object.class);
            return true;
        }
        catch (HttpClientErrorException.Conflict e) {
            return false;
        }
    }

    public static List<TagVersionDto> getTagVersions(String remote, String tagName) {
        RestTemplate restTemplate = new RestTemplate();
        var array = restTemplate.getForObject(remote + "/tagversion?tagName=" + tagName, TagVersionDto[].class); // TODO encoding
        return array == null ? null : Arrays.asList(array);
    }

    public static List<FileVersionDto> getFileVersions(String remote, String fileName) {
        RestTemplate restTemplate = new RestTemplate();
        var array = restTemplate.getForObject(remote + "/fileversion?fileName=" + fileName, FileVersionDto[].class); // TODO encoding
        return array == null ? null : Arrays.asList(array);
    }

    public static TagVersionDto getTagVersion(String remote, String tagVersion) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(remote + "/tagversion/" + tagVersion, TagVersionDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public static FileVersionDto getFileVersion(String remote, String fileId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(remote + "/fileversion/" + fileId, FileVersionDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
