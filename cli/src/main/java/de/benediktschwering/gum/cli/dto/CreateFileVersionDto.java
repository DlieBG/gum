package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateFileVersionDto {
    @NonNull
    private String fileName;
    @NonNull
    private String user;
}
