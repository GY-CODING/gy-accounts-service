package org.gycoding.accounts.domain.entities.metadata.gymessages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYMessagesMetadata(
        List<ChatMetadata> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}