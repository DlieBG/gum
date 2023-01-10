package de.benediktschwering.gum.cli.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DeleteLockDto {
    @NonNull
    private String user;
}
