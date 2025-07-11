package org.gycoding.accounts.application.dto.in.user.metadata.messages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record MessagesMetadataIDTO(
        List<ChatIDTO> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}