package org.gycoding.accounts.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AccountsAPIError {
    INVALID_LOGIN("Invalid login due to the non existance of the user or invalid password.", HttpStatus.UNAUTHORIZED),
    INVALID_SIGNUP("Invalid sign up due to the already existance of the user or an authentication error.", HttpStatus.CONFLICT),

    RESOURCE_NOT_MODIFIED("The resource could not be modified due to some internal conflict or error.", HttpStatus.NOT_MODIFIED),
    RESOURCE_NOT_FOUND("This resource was not found.", HttpStatus.NOT_FOUND),
    CONFLICT("An internal conflict between external data and the API has occured. Requested data may already exist.", HttpStatus.CONFLICT),
    FORBIDDEN("The user has no permission to access this resource.", HttpStatus.FORBIDDEN),
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