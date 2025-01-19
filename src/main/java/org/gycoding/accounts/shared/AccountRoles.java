package org.gycoding.accounts.shared;

import lombok.Getter;

public enum AccountRoles {
    COMMON("COMMON"),
    ADMIN("ADMIN");

    @Getter
    public final String role;

    private AccountRoles(String role) {
        this.role    = role;
    }

    public static AccountRoles fromString(String roleString) {
        for (AccountRoles role : AccountRoles.values()) {
            if (role.role.equals(roleString)) {
                return role;
            }
        }
        return null;
    }
}