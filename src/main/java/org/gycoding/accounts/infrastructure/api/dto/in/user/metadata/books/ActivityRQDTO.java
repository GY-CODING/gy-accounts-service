package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books;

import lombok.Builder;

import java.util.List;

@Builder
public record ActivityRQDTO(
        String message
) { }
