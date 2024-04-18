package org.gycoding.accounts.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

public record UserRQDTO(
    @JsonProperty("user")
    String user,
    @JsonProperty("email")
    String email,
    @JsonProperty("password")
    String password,

    // TODO. Review this when implementing Hexagonal Architecture.
    @JsonProperty("new-user")
    String newUsername,
    @JsonProperty("new-email")
    String newEmail,
    @JsonProperty("new-password")
    String newPassword
) {
    @Builder
    public UserRQDTO {
    }
}