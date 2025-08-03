package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record HallOfFameRQDTO(
        List<String> books,
        String quote
) { }
