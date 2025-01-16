package org.gycoding.accounts.application.dto.out.user.metadata.gyclient;

import lombok.Builder;

@Builder
public record FriendMetadataODTO(
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