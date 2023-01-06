package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLockDto {
    private String fileNameRegex;
    private String tagNameRegex;
    private String user;
}
