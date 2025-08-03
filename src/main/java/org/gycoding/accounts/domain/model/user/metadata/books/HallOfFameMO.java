package org.gycoding.accounts.domain.model.user.metadata.books;

import lombok.Builder;

import java.util.List;

@Builder
public record HallOfFameMO(
        List<String> books,
        String quote
) { }
