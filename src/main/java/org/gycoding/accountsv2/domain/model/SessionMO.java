package org.gycoding.accountsv2.domain.model;

import lombok.Builder;

public record SessionMO(
        UserMO user,
        String jwt
) {
    @Builder
    public SessionMO {}

    @Override
    public String toString() {
        return String.format("{\"user\": %s, \"jwtToken\": %s}", user.toString(), jwt);
    }
}
