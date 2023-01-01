package de.benediktschwering.gum.server.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document
@Getter
@Setter
@RequiredArgsConstructor
public class Repository {

    @Id
    private String id;

    @NonNull
    private String name;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'repository': ?#{#self._id} }", lazy = true)
    private List<FileVersion> fileVersions;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'repository': ?#{#self._id} }", lazy = true)
    private List<TagVersion> tagVersions;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'repository': ?#{#self._id} }", lazy = true)
    private List<Lock> locks;

}
