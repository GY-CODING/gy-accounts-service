package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gyclient;

import lombok.Builder;

@Builder
public record FriendMetadataRQDTO(
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