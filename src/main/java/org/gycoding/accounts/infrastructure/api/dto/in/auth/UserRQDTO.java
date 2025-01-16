package org.gycoding.accounts.infrastructure.api.dto.in.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserRQDTO(
    String email,
    String username,
    @NotEmpty(message = "Password is required.")
    String password
) { }