package de.benediktschwering.gum.server.model;

import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import de.benediktschwering.gum.server.controller.FileVersionController;
import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.LogManager;

@Document
@Getter
@Setter
@RequiredArgsConstructor
public class FileVersion {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(FileVersion.class.getName());
    private static final LogManager LOGMANAGER = LogManager.getLogManager();

    static {
        try {
            LOGMANAGER.readConfiguration(new FileInputStream("./aufgabe2.properties"));
        } catch (IOException e) {
            LOGGER.error("Konfigurationsdatei nicht gefunden", e);
            throw new RuntimeException(e);
        }
    }

    @Id
    private String id;
    @NonNull
    @DocumentReference
    private Repository repository;
    @NonNull
    private String fileName;
    @NonNull
    private String user;
    private ObjectId gridFsId;
    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'fileVersions': { $in: [ ?#{#self._id} ] } }", lazy = true)
    private List<TagVersion> tagVersions;
    public Optional<GridFSFile> getFile(
            GridFsTemplate gridFsTemplate
    ) {
        return getGridFsId() == null ? Optional.empty() : Optional.ofNullable(
                gridFsTemplate.findOne(
                        new Query(
                                Criteria.where("_id").is(getGridFsId())
                        )
                )
        );
    }
    public void setFile(
            InputStream inputStream,
            String contentType,
            GridFsTemplate gridFsTemplate
    ) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            inputStream.transferTo(outputStream);

            InputStream inputStream1 = new ByteArrayInputStream(outputStream.toByteArray());
            InputStream inputStream2 = new ByteArrayInputStream(outputStream.toByteArray());
            InputStream inputStream3 = new ByteArrayInputStream(outputStream.toByteArray());

            BasicDBObject metadata = new BasicDBObject();
            metadata.put("md5", DigestUtils.md5Hex(inputStream1));
            metadata.put("sha256", DigestUtils.sha256Hex(inputStream2));

            ObjectId gridFsId = gridFsTemplate.store(inputStream3, fileName, contentType, metadata);

            setGridFsId(gridFsId);
        } catch(IOException e) {
            LOGGER.error("Fehler beim Speichern der Datei", e);
            throw new RuntimeException(e);
        }
    }
}
