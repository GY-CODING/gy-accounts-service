package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.books;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksMetadataRSDTO(
        List<String> friends
) { }
