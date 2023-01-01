package de.benediktschwering.gum.server.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document
@Getter
@Setter
@RequiredArgsConstructor
public class TagVersion {

    @Id
    private String id;

    @NonNull
    @DocumentReference
    private Repository repository;

    @NonNull
    private String tagname;

    @NonNull
    private String user;

    @NonNull
    @DocumentReference
    private List<FileVersion> fileVersions;

}
