package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books;

import lombok.Builder;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestCommand;

@Builder
public record FriendRequestRQDTO(
        FriendRequestCommand command,
        String to
) { }
