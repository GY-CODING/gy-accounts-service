package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books;

import lombok.Builder;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestCommand;

import java.util.UUID;

@Builder
public record FriendRequestRQDTO(
        FriendRequestCommand command,
        UUID to
) { }
