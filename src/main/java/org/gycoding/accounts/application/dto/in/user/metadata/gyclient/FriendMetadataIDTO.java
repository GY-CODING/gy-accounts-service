package org.gycoding.accounts.application.dto.in.user.metadata.gyclient;

import lombok.Builder;

@Builder
public record FriendMetadataIDTO(
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