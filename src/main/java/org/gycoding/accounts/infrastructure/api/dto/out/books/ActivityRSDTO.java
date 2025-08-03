package org.gycoding.accounts.infrastructure.api.dto.out.books;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ActivityRSDTO(
        UUID id,
        String message
) { }
