package org.gycoding.accounts.domain.model.user.metadata.messages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record MessagesMetadataMO(
        List<ChatMO> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}