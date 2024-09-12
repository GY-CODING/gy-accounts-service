package org.gycoding.accounts.domain.entities.username;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Builder
@Document(collection = "AuthUsername")
public record EntityUsername (
    @Id
    String mongoId,
    String userId,
    String username
) {
    @Override
    public String toString() {
        return "{" +
                "\"userId\":\"" + userId + "\"," +
                "\"username\":\"" + username + "\"" +
                '}';
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "userId", userId,
                "username", username
        );
    }
}