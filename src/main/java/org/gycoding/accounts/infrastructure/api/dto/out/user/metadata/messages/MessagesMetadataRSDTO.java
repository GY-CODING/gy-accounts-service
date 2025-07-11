package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.messages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record MessagesMetadataRSDTO(
        List<ChatRSDTO> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}