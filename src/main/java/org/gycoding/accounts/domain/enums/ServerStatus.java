package org.gycoding.accounts.domain.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServerStatus {
    HOME_NOT_FOUND("API reference not found.", HttpStatus.NOT_FOUND),

    INVALID_SIGNUP("SignUp procedure went wrong. Possible causes could be the user already exists or an internal error from the authentication service.", HttpStatus.CONFLICT),
    INVALID_LOGIN("LogIn procedure went wrong. Possible causes could be invalid credentials provided when trying to log in or an internal error from the authentication service.", HttpStatus.UNAUTHORIZED),

    COULD_NOT_GET_USER_METADATA("Authentication service could not get user metadata successfully.", HttpStatus.NOT_FOUND),
    METADATA_NOT_UPDATED("Authentication service could not update metadata successfully.", HttpStatus.NOT_MODIFIED),
    ACHIEVEMENTS_NOT_FOUND("Achievements not found.", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_JWT_FORMAT("Invalid JWT format", HttpStatus.BAD_REQUEST),

    BAD_REQUEST("The endpoint is malformed.", HttpStatus.BAD_REQUEST),
    AUTH_ERROR("An error with the authentication service has occurred, sorry for the inconvenience.", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVER_ERROR("An internal server error has occurred, sorry for the inconvenience.", HttpStatus.INTERNAL_SERVER_ERROR);

    public final String code;
    public final String message;
    public final HttpStatus status;

    private ServerStatus(String message, HttpStatus status) {
        this.code       = this.name();
        this.message    = message;
        this.status     = status;
    }
}