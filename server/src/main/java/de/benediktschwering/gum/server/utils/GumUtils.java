package de.benediktschwering.gum.server.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

public class GumUtils {

    static Logger logger = Logger.getLogger("app");

    public static ResponseStatusException NotFound() {
        logger.severe("HTTPStatus not found");
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static ResponseStatusException Conflict() {
        logger.severe("HTTPStatus conflict");
        return new ResponseStatusException(HttpStatus.CONFLICT);
    }

}
