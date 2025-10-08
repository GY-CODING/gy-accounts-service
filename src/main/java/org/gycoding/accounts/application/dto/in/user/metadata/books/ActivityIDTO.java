package org.gycoding.accounts.application.dto.in.user.metadata.books;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ActivityIDTO(
        UUID id,
        String message,
        Date date
) { }
