package de.benediktschwering.gum.server.model;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Getter
@Setter
@RequiredArgsConstructor
public class Lock {
    @Id
    private String id;
    @NonNull
    @DocumentReference
    private Repository repository;
    @Nullable
    private String fileNameRegex;
    @Nullable
    private String tagNameRegex;
    @NonNull
    private String user;
}
