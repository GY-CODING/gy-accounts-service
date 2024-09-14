package org.gycoding.accounts.domain.entities.model.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class User {
    private String username;
    private String email;
    private String password;

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