package org.gycoding.accounts.domain.model.user.metadata.gymessages;

import lombok.Builder;

@Builder
public record ChatMO(
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