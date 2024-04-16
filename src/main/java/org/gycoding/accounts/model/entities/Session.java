package org.gycoding.accounts.model.entities;

import lombok.Builder;

public record Session(
        User user
) {
    @Builder
    public Session {}

    @Override
    public String toString() {
        return String.format("{\"user\": %s}", user.toString());
    }
}
