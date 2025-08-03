package org.gycoding.accounts.application.dto.out.books;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ActivityODTO(
        UUID id,
        String message
) { }
