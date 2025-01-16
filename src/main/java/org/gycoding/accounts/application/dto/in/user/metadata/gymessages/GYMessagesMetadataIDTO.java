package org.gycoding.accounts.application.dto.in.user.metadata.gymessages;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYMessagesMetadataIDTO(
        List<ChatMetadataIDTO> chats
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "chats", chats
        );
    }
}