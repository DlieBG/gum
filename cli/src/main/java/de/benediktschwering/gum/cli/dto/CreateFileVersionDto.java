package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFileVersionDto {
    private String repositoryId;
    private String filename;
    private String user;
}
