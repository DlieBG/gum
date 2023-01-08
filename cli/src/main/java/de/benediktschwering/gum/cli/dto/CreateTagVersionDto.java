package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateTagVersionDto {
    @NonNull
    private String tagName;
    @NonNull
    private String user;
    @NonNull
    private boolean hard;
    @NonNull
    private List<String> fileVersionIds;
}
