package org.gycoding.accounts.infrastructure.api.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.books.ActivityIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.books.HallOfFameIDTO;
import org.gycoding.accounts.application.dto.out.books.ActivityODTO;
import org.gycoding.accounts.application.dto.out.books.BooksProfileODTO;
import org.gycoding.accounts.application.dto.out.books.HallOfFameODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books.ActivityRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books.HallOfFameRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.books.ActivityRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.books.BooksProfilePublicRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.books.BooksProfileRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.books.HallOfFameRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.books.FriendRequestRSDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class, Date.class })
public interface BooksControllerMapper {
    FriendRequestRSDTO toRSDTO(FriendRequestODTO friendRequest);

    BooksProfileRSDTO toRSDTO(BooksProfileODTO booksProfile);

    BooksProfilePublicRSDTO toPublicRSDTO(BooksProfileODTO booksProfile);

    HallOfFameIDTO toIDTO(HallOfFameRQDTO hallOfFame);

    HallOfFameRSDTO toRSDTO(HallOfFameODTO hallOfFame);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "date", expression = "java(new Date())")
    ActivityIDTO toIDTO(ActivityRQDTO activity);

    ActivityRSDTO toRSDTO(ActivityODTO activity);
}
