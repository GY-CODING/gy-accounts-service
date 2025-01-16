package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gymessages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYMessagesMetadataRSDTO(
        List<ChatMetadataRSDTO> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}