package org.gycoding.accounts.infrastructure.api.dto.in.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserRQDTO(
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format.")
    String email,
    String username,
    @NotEmpty(message = "Password is required.")
    String password
) { }