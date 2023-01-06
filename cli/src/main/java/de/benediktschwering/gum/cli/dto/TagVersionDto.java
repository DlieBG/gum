package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagVersionDto {
    private String id;
    private RepositoryDto repository;
    private String tagname;
    private String user;
    private List<FileVersionDto> fileVersions;
}
