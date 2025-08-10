package org.gycoding.accounts.application.dto.out.user.metadata.books;

import lombok.Builder;
import org.gycoding.accounts.application.dto.out.books.ActivityODTO;
import org.gycoding.accounts.application.dto.out.books.HallOfFameODTO;

import java.util.List;
import java.util.UUID;

@Builder
public record BooksMetadataODTO(
        List<String> friends,
        String biography,
        HallOfFameODTO hallOfFame,
        List<ActivityODTO> activity
) { }
