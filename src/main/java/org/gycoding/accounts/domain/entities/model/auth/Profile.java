package org.gycoding.accounts.domain.entities.model.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class Profile {
    private String username;
    private String email;
    private String picture;
    private List<String> roles;
    private String phoneNumber;

    @Override
    public String toString() {
        return "{" +
                "\"email\":\"" + email + "\"," +
                "\"username\":\"" + username + "\"" +
                "\"picture\":\"" + picture + "\"" +
                "\"roles\":\"" + roles + "\"" +
                "\"phoneNumber\":\"" + phoneNumber + "\"" +
                '}';
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "email", email,
                "username", username,
                "picture", picture,
                "roles", roles,
                "phoneNumber", phoneNumber
        );
    }
}