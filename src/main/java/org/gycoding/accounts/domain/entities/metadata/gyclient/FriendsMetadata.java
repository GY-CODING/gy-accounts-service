package org.gycoding.accounts.domain.entities.metadata.gyclient;

import lombok.Builder;

@Builder
public record FriendsMetadata(
        String userId,
        String username
) {
    @Override
    public String toString() {
        return "{"
                + "\"userId\": \"" + userId + "\","
                + "\"username\": " + username
                + "}";
    }
}