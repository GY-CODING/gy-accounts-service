package org.gycoding.accounts.application.dto.out.user.metadata.messages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record MessagesMetadataODTO(
        List<ChatMetadataODTO> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}