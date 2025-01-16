package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gymessages;

import lombok.Builder;

@Builder
public record ChatMetadataRSDTO(
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