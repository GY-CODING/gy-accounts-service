package org.gycoding.accounts.application.dto.out.user.metadata.books;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksMetadataODTO(
        List<String> friends,
        String biography
) { }
