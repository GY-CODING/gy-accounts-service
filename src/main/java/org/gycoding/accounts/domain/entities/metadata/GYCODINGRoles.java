package org.gycoding.accounts.domain.entities.metadata;

import lombok.Getter;

public enum GYCODINGRoles {
    COMMON("COMMON"),
    ADMIN("ADMIN");

    @Getter
    public final String role;

    private GYCODINGRoles(String role) {
        this.role    = role;
    }
}