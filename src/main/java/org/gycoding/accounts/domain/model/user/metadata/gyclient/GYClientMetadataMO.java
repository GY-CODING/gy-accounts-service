package org.gycoding.accounts.domain.model.user.metadata.gyclient;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record GYClientMetadataMO(
        String title,
        List<FriendMetadataMO> friends
) {
    public Map<String, Object> toMap() {
        return Map.of(
                "title", title,
                "friends", friends
        );
    }
}