package org.gycoding.accounts.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Builder
@Document(collection = "User")
public record User(
        @Id
        String mongoId,
        String username,
        String email,
        String password,
        String salt
) {
    @Override
    public String toString() {
        return "{" +
                "\"email\":\"" + email + "\"," +
                "\"username\":\"" + username + "\"" +
                '}';
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "email", email,
                "username", username
        );
    }
}