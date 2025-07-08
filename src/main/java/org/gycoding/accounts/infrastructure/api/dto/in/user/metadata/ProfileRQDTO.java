package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ProfileRQDTO(
    @NotEmpty(message = "Username is required.")
    String username,
    @NotEmpty(message = "Profile picture is required.")
    String picture,
    @Pattern(regexp = "^\\+\\d{1,3}\\s\\d{4,14}$", message = "Invalid phone number format.")
    @NotEmpty(message = "Phone number is required (and must use the international prefix separated from the other numbers by a blank space).")
    String phoneNumber
) { }