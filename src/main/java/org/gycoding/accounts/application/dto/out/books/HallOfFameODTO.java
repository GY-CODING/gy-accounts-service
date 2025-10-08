package org.gycoding.accounts.application.dto.out.books;

import lombok.Builder;

import java.util.List;

@Builder
public record HallOfFameODTO(
        List<String> books,
        String quote
) { }
