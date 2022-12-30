package de.benediktschwering.gum.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@RequiredArgsConstructor
public class Lock {

    @Id
    private String id;

    @NonNull
    private String name;

}
