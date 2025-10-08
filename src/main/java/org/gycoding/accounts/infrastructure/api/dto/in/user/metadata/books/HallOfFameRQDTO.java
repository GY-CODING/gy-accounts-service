package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books;

import lombok.Builder;

@Builder
public record HallOfFameRQDTO(
        String bookId,
        String quote
) { }
