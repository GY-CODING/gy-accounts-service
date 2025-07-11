package org.gycoding.accounts.application.dto.out.user.metadata.messages;

import lombok.Builder;

@Builder
public record ChatMetadataODTO(
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