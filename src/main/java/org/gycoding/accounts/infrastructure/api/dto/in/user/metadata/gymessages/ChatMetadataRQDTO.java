package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages;

import lombok.Builder;

@Builder
public record ChatMetadataRQDTO(
        String chatId,
        boolean isAdmin
) {
    @Override
    public String toString() {
        return "{"
                + "\"chatId\": \"" + chatId + "\","
                + "\"isAdmin\": " + isAdmin
                + "}";
    }
}