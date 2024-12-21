package org.gycoding.accounts.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AccountsAPIError {
    INVALID_SIGNUP("SignUp procedure went wrong. Possible causes could be the user already exists or an internal error from the authentication service.", HttpStatus.CONFLICT),
    INVALID_LOGIN("LogIn procedure went wrong. Possible causes could be invalid credentials provided when trying to log in or an internal error from the authentication service.", HttpStatus.UNAUTHORIZED),

    USERNAME_NOT_SAVED("The username could not be saved due to an authentication service error.", HttpStatus.CONFLICT),
    PICTURE_NOT_SAVED("The user's picture could not be saved due to an input error.", HttpStatus.CONFLICT),

    RESOURCE_NOT_MODIFIED("The resource could not be modified due to some internal conflict or error.", HttpStatus.NOT_MODIFIED),
    RESOURCE_NOT_FOUND("This resource was not found.", HttpStatus.NOT_FOUND),
    CONFLICT("An internal conflict between external data and the API has occured. Requested data may already exist.", HttpStatus.CONFLICT),
    AUTH_ERROR("An error with the authentication service has occurred, sorry for the inconvenience.", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVER_ERROR("An internal server error has occurred, sorry for the inconvenience.", HttpStatus.INTERNAL_SERVER_ERROR);

    public final String code;
    public final String message;
    public final HttpStatus status;

    AccountsAPIError(String message, HttpStatus status) {
        this.code       = this.name();
        this.message    = message;
        this.status     = status;
    }
}