package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gyclient;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYClientMetadataRQDTO(
        String title,
        List<FriendMetadataRQDTO> friends
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "title", title,
                "friends", friends
        );
    }
}