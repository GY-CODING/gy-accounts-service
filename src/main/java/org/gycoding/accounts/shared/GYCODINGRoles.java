package org.gycoding.accounts.shared;

import lombok.Getter;

public enum GYCODINGRoles {
    COMMON("COMMON"),
    ADMIN("ADMIN");

    @Getter
    public final String role;

    private GYCODINGRoles(String role) {
        this.role    = role;
    }

    public static GYCODINGRoles fromString(String roleString) {
        for (GYCODINGRoles role : GYCODINGRoles.values()) {
            if (role.role.equals(roleString)) {
                return role;
            }
        }
        return null;
    }
}