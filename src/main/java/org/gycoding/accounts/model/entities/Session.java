package org.gycoding.accounts.model.entities;

import lombok.Builder;

public record Session(
        User user,
        String jwt
) {
    @Builder
    public Session {}

    @Override
    public String toString() {
        return String.format("{\"user\": %s, \"jwtToken\": %s}", user.toString(), jwt);
    }
}
