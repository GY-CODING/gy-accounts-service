package org.gycoding.accounts.domain.model.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserMO(
    String email,
    String username,
    @NotEmpty(message = "Password is required.")
    String password
) { }