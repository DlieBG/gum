package de.benediktschwering.gum.cli.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTagVersionDto {
    private String tagName;
    private String user;
    private List<String> fileVersionIds;
}
