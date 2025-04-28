package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record ChatRQDTO(
    @NotEmpty(message = "Chat ID is required.")
    String chatId,
    @NotEmpty(message = "User being an admin or not is required.")
    Boolean isAdmin
) { }