package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.books;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FriendRequestRSDTO(
        UUID id,
        String from,
        String to
) { }