package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gyclient;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYClientMetadataRSDTO(
        String title,
        List<FriendMetadataRSDTO> friends
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "title", title,
                "friends", friends
        );
    }
}