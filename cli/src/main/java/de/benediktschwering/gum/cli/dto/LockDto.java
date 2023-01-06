package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockDto {
    private String id;
    private RepositoryDto repository;
    private String filenameRegex;
    private String tagnameRegex;
    private String user;
}
