package org.gycoding.accounts.domain.entities.metadata.gymessages;

import lombok.Builder;

@Builder
public record ChatMetadata(
        String chatId,
        boolean isAdmin
) { }