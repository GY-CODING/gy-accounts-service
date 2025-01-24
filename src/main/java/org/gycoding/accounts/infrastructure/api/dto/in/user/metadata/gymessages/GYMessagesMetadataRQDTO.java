package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYMessagesMetadataRQDTO(
        List<ChatRQDTO> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}