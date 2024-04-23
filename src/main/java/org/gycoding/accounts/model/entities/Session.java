package org.gycoding.accounts.model.entities;

import lombok.Builder;

@Builder
public record Session(
        User user
) {
    @Override
    public String toString() {
        return String.format("{\"user\": %s}", user.toString());
    }
}
