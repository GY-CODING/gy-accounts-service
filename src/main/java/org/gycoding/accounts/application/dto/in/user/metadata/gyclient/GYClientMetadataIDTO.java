package org.gycoding.accounts.application.dto.in.user.metadata.gyclient;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYClientMetadataIDTO(
        String title,
        List<FriendMetadataIDTO> friends
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "title", title,
                "friends", friends
        );
    }
}