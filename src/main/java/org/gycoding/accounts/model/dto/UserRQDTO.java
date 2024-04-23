package org.gycoding.accounts.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record UserRQDTO(
    @JsonProperty("email")
    String email,
    @JsonProperty("user")
    String user,
    @JsonProperty("password")
    String password,

    @JsonProperty("new-user")
    String newUsername,
    @JsonProperty("new-email")
    String newEmail,
    @JsonProperty("new-password")
    String newPassword
) {}