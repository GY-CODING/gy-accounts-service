package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.messages;

import lombok.Builder;

@Builder
public record ChatRSDTO(
    String chatId,
    Boolean isAdmin
) { }