package org.gycoding.accounts.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

@Builder
public record UserRQDTO(
    @JsonProperty("email")
    String email,
    @JsonProperty("user")
    String user,
    @JsonProperty("password")
    @NotEmpty(message = "Password is required.")
    String password
) { }