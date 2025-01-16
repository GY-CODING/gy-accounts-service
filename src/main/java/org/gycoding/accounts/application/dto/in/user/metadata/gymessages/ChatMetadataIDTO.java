package org.gycoding.accounts.application.dto.in.user.metadata.gymessages;

import lombok.Builder;

@Builder
public record ChatMetadataIDTO(
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