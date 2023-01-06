package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVersionDto {
    private String id;
    private RepositoryDto repository;
    private String filename;
    private String user;
    private boolean deleted = true;
    private String md5;
    private String sha256;
}
