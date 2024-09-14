package org.gycoding.accounts.domain.entities.model.gyclient;

import lombok.Builder;
import org.gycoding.accounts.domain.entities.metadata.gyclient.FriendsMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.ChatMetadata;

import java.util.List;
import java.util.Map;

@Builder
public record Profile(
        String username,
        String title
) {
    @Override
    public String toString() {
        return "{" +
                "\"username\":\"" + username + "\"," +
                "\"title\":\"" + title + "\"" +
                '}';
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "username", username,
                "title", title
        );
    }
}