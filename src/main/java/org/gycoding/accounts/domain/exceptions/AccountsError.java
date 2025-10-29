package org.gycoding.accounts.domain.exceptions;

import lombok.Getter;
import org.gycoding.quasar.exceptions.model.ExceptionError;
import org.springframework.http.HttpStatus;

@Getter
public enum AccountsError implements ExceptionError {
    INVALID_LOGIN("Invalid login due to the non existance of the username or invalid password.", HttpStatus.UNAUTHORIZED),
    INVALID_SIGNUP("Invalid sign up due to the already existance of the username or an authentication error.", HttpStatus.CONFLICT),

    USER_NOT_FOUND("User was not found on the accounts system.", HttpStatus.NOT_FOUND),
    PASSWORD_NOT_MODIFIED("User password could not be updated.", HttpStatus.NOT_MODIFIED),
    METADATA_NOT_FOUND("User metadata was not found.", HttpStatus.NOT_FOUND),
    METADATA_NOT_SAVED("User metadata could not be saved.", HttpStatus.INTERNAL_SERVER_ERROR),
    METADATA_NOT_MODIFIED("User metadata could not be updated.", HttpStatus.NOT_MODIFIED),

    RESOURCE_NOT_MODIFIED("The resource could not be modified due to some internal conflict or error.", HttpStatus.NOT_MODIFIED),
    RESOURCE_NOT_FOUND("This resource was not found.", HttpStatus.NOT_FOUND),
    CONFLICT("An internal conflict between external data and the API has occured. Requested data may already exist.", HttpStatus.CONFLICT),
    AUTH_ERROR("An error with the authentication service has occurred, sorry for the inconvenience.", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVER_ERROR("An internal server error has occurred, sorry for the inconvenience.", HttpStatus.INTERNAL_SERVER_ERROR);

    public final String code;
    public final String message;
    public final HttpStatus status;

    AccountsError(String message, HttpStatus status) {
        this.code       = this.name();
        this.message    = message;
        this.status     = status;
    }
}