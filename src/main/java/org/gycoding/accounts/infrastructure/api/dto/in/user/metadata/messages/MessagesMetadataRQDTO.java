package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.messages;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record MessagesMetadataRQDTO(
        @NotEmpty(message = "Chats for GY Messages are required.")
        List<ChatRQDTO> chats
) { }