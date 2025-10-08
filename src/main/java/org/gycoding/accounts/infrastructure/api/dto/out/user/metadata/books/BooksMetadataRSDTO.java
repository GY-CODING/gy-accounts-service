package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.books;

import lombok.Builder;
import org.gycoding.accounts.infrastructure.api.dto.out.books.ActivityRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.books.HallOfFameRSDTO;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksMetadataRSDTO(
        List<String> friends,
        String biography,
        HallOfFameRSDTO hallOfFame,
        List<ActivityRSDTO> activity
) { }
