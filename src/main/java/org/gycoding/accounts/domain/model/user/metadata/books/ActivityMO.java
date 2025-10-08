package org.gycoding.accounts.domain.model.user.metadata.books;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ActivityMO(
        UUID id,
        String message,
        Date date
) { }
