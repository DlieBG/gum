package de.benediktschwering.gum.server.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GumUtils {

    public static ResponseStatusException NotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static ResponseStatusException Conflict() {
        return new ResponseStatusException(HttpStatus.CONFLICT);
    }

}
