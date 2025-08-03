package org.gycoding.accounts.application.dto.in.user.metadata.books;

import lombok.Builder;

import java.util.List;

@Builder
public record HallOfFameIDTO(
        List<String> books,
        String quote
) { }
