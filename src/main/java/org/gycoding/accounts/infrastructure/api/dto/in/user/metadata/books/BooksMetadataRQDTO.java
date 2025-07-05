package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksMetadataRQDTO(
        List<String> friends
) { }
