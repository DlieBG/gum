package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockDto {
    private String id;
    private RepositoryDto repository;
    private String fileNameRegex;
    private String tagNameRegex;
    private String user;
}
