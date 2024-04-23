package org.gycoding.accounts.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Builder
@Getter
@Setter
@Document(collection = "User")
public class User {
    @Id
    private String mongoId;
    private String username;
    private String email;
    private String password;
    private String salt;

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