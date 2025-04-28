package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record GYMessagesMetadataRQDTO(
        @NotEmpty(message = "Chats for GY Messages are required.")
        List<ChatRQDTO> chats
) { }