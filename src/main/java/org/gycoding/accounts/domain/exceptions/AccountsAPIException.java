package org.gycoding.accounts.domain.exceptions;

import lombok.Getter;
import org.gycoding.accounts.domain.enums.ServerStatus;

@Getter
public class AccountsAPIException extends Exception {
    private final ServerStatus status;

    public AccountsAPIException(ServerStatus status) {
        super(status.message);
        this.status = status;
    }
}
