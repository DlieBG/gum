package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryDto {
    private String id;
    private String name;
    private List<String> tagNames;
}
