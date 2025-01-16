package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gyclient;

import lombok.Builder;

@Builder
public record FriendMetadataRSDTO(
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