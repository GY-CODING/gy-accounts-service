package org.gycoding.accounts.domain.model.user.metadata.gymessages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYMessagesMetadataMO(
        List<ChatMO> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}