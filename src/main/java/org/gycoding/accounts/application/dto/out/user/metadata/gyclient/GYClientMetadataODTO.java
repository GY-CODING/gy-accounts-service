package org.gycoding.accounts.application.dto.out.user.metadata.gyclient;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYClientMetadataODTO(
        String title,
        List<FriendMetadataODTO> friends
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "title", title,
                "friends", friends
        );
    }
}