package de.benediktschwering.gum.server.controller;

import com.mongodb.client.gridfs.model.GridFSFile;
import de.benediktschwering.gum.server.dto.CreateFileVersionDto;
import de.benediktschwering.gum.server.dto.FileVersionDto;
import de.benediktschwering.gum.server.model.FileVersion;
import de.benediktschwering.gum.server.model.Repository;
import de.benediktschwering.gum.server.repository.FileVersionRepository;
import de.benediktschwering.gum.server.repository.RepositoryRepository;
import de.benediktschwering.gum.server.utils.GumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fileversion")
public class FileVersionController {

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Autowired
    private FileVersionRepository fileVersionRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    @GetMapping("")
    public List<FileVersionDto> getFileVersions(
            @RequestParam("repositoryId") String repositoryId,
            @RequestParam("filename") String filename
    ) {
        Repository repository = repositoryRepository
                .findById(repositoryId)
                .orElseThrow(GumUtils::NotFound);

        return fileVersionRepository
                .searchFileVersionsByRepositoryAndFilenameOrderByIdAsc(
                        repository,
                        filename
                )
                .stream()
                .map(
                        (FileVersion fileVersion) ->
                                new FileVersionDto(
                                        fileVersion,
                                        gridFsTemplate
                                )
                )
                .toList();
    }

    @GetMapping("/{id}")
    public FileVersionDto getFileVersion(
            @PathVariable("id") String id
    ) {
        return new FileVersionDto(
                fileVersionRepository
                        .findById(id)
                        .orElseThrow(GumUtils::NotFound),
                gridFsTemplate
        );
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getFileVersionFile(
            @PathVariable("id") String id
    ) {
        GridFSFile file = fileVersionRepository
                .findById(id)
                .orElseThrow(GumUtils::NotFound)
                .getFile(gridFsTemplate)
                .orElseThrow(GumUtils::NotFound);

        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.asMediaType(
                                MimeType.valueOf(
                                        file
                                                .getMetadata()
                                                .get("_contentType")
                                                .toString()
                                )
                        )
                )
                .header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"")
                .body(gridFsOperations.getResource(file));
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public FileVersionDto createFileVersion(
            @RequestBody CreateFileVersionDto createFileVersion
    ) {
        return new FileVersionDto(
                fileVersionRepository.save(
                        createFileVersion.toFileVersion(repositoryRepository)
                ),
                gridFsTemplate
        );
    }

    @PostMapping("/{id}/file")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createFileVersionFile(
            @PathVariable("id") String id,
            @RequestPart("file") FilePart filePart
    ) {
        FileVersion fileVersion = fileVersionRepository
                .findById(id)
                .orElseThrow(GumUtils::NotFound);

        fileVersion.setFile(
                filePart.content().reduce(DataBuffer::write).block().asInputStream(),
                filePart.headers().getContentType().toString(),
                gridFsTemplate
        );

        fileVersionRepository.save(fileVersion);
    }

}
