package org.gycoding.accounts.domain.model.user.metadata.gyclient;

import lombok.Builder;

@Builder
public record FriendMetadataMO(
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