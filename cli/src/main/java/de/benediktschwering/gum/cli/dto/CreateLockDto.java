package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateLockDto {
    @NonNull
    private String fileNameRegex;
    @NonNull
    private String tagNameRegex;
    @NonNull
    private String user;
}
