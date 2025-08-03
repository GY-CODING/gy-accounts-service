package org.gycoding.accounts.infrastructure.api.dto.out.books;

import lombok.Builder;

import java.util.List;

@Builder
public record HallOfFameRSDTO(
        List<String> books,
        String quote
) { }
