package org.gycoding.accounts.application.dto.in.user.metadata.books;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksMetadataIDTO(
        List<String> friends
) { }
