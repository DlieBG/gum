package de.benediktschwering.gum.cli.dto;

import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateLockDto {
    @Nullable
    private String fileNameRegex;
    @Nullable
    private String tagNameRegex;
    @NonNull
    private String user;
}
