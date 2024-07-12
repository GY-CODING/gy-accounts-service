package org.gycoding.accounts.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ChatRQDTO(
    @JsonProperty("chatId")
    String chatId,
    @JsonProperty("admin")
    Boolean isAdmin
) {}