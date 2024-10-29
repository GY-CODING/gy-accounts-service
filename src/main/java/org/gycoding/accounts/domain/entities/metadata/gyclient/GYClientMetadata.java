package org.gycoding.accounts.domain.entities.metadata.gyclient;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYClientMetadata(
        String title,
        List<FriendsMetadata> friends
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "title", title,
                "friends", friends
        );
    }
}