package org.gycoding.accounts.application.dto.out.user.metadata.books;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FriendRequestODTO(
        UUID id,
        String from,
        String to
) { }