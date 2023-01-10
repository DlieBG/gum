package de.benediktschwering.gum.cli.utils;

import de.benediktschwering.gum.cli.dto.FileVersionDto;
import de.benediktschwering.gum.cli.dto.TagVersionDto;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GumConfig {
    @NonNull
    private String remote;
    @NonNull
    private String user;
    @NonNull
    private TagVersionDto baseTagVersion;
    @NonNull
    private List<FileVersionDto> localFileVersions;
}
