package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record ChatRQDTO(
    @JsonProperty("chatId")
    @NotEmpty(message = "Chat ID is required.")
    String chatId,
    @JsonProperty("admin")
    Boolean isAdmin
) { }