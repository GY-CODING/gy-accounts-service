package org.gycoding.accounts.application.dto.in.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserIDTO(
    String email,
    String username,
    @NotEmpty(message = "Password is required.")
    String password
) { }