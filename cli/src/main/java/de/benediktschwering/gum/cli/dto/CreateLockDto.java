package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLockDto {
    private String repositoryId;
    private String filenameRegex;
    private String tagnameRegex;
    private String user;
}
