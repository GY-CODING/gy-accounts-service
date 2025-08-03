package org.gycoding.accounts.domain.model.user.metadata.books;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksMetadataMO(
        List<UUID> friends,
        String biography,
        HallOfFameMO hallOfFame
) { }
