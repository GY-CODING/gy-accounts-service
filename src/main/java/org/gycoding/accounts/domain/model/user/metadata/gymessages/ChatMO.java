package org.gycoding.accounts.domain.model.user.metadata.gymessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record ChatMO(
    @JsonProperty("chatId")
    @NotEmpty(message = "Chat ID is required.")
    String chatId,
    @JsonProperty("admin")
    Boolean isAdmin
) { }