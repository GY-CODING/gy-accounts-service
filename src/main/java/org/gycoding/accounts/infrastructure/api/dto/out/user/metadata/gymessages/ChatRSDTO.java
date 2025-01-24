package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gymessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record ChatRSDTO(
    @JsonProperty("chatId")
    String chatId,
    @JsonProperty("isAdmin")
    Boolean isAdmin
) { }