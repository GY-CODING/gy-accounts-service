package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record HallOfFameRQDTO(
        @Size(max = 5, message = "You can only add up to 5 books in the Hall of Fame.")
        List<String> books,
        String quote
) { }
