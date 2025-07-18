package org.gycoding.accounts.domain.model.user.metadata.books;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FriendRequestMO(
        UUID id,
        UUID from,
        UUID to
) { }