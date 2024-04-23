package org.gycoding.accounts.model.entities;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServerStatus {
    HOME_NOT_FOUND("API reference not found.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("User not found.", HttpStatus.NOT_FOUND),

    USER_REGISTERED("User has been successfully registered.", HttpStatus.CREATED),
    INVALID_SIGNUP("User already exists.", HttpStatus.CONFLICT),

    USER_LOGGED_IN("User has been successfully logged in.", HttpStatus.OK),
    INVALID_LOGIN("Invalid credentials.", HttpStatus.UNAUTHORIZED),

    USERNAME_UPDATE("Username updated successfully.", HttpStatus.OK),
    USERNAME_UPDATE_ERROR("Username update went wrong.", HttpStatus.CONFLICT),

    EMAIL_UPDATE("Email updated successfully.", HttpStatus.OK),
    EMAIL_UPDATE_ERROR("Email update went wrong.", HttpStatus.CONFLICT),

    PASSWORD_UPDATE("Password updated successfully.", HttpStatus.OK),
    PASSWORD_UPDATE_ERROR("Password update went wrong.", HttpStatus.CONFLICT),

    BAD_REQUEST("The endpoint is malformed.", HttpStatus.BAD_REQUEST),
    SERVER_ERROR("An internal server error has occurred, sorry for the inconvenience.", HttpStatus.INTERNAL_SERVER_ERROR);

    public final String msg;
    public final HttpStatus status;

    private ServerStatus(String msg, HttpStatus status) {
        this.msg    = msg;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("{\n\t\"error\": \"%s\",\n\n\t\"status\": %d,\n\t\"message\": \"%s\"}", this.status.getReasonPhrase().toUpperCase(), this.status.value(), this.msg);
    }
}