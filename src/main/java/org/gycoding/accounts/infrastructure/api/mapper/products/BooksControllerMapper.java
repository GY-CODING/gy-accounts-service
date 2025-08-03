package org.gycoding.accounts.infrastructure.api.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.books.HallOfFameIDTO;
import org.gycoding.accounts.application.dto.out.books.BooksProfileODTO;
import org.gycoding.accounts.application.dto.out.books.HallOfFameODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books.HallOfFameRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.books.BooksProfilePublicRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.books.BooksProfileRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.books.HallOfFameRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.books.FriendRequestRSDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BooksControllerMapper {
    FriendRequestRSDTO toRSDTO(FriendRequestODTO friendRequest);

    BooksProfileRSDTO toRSDTO(BooksProfileODTO booksProfile);

    BooksProfilePublicRSDTO toPublicRSDTO(BooksProfileODTO booksProfile);

    HallOfFameIDTO toIDTO(HallOfFameRQDTO hallOfFame);

    HallOfFameRSDTO toRSDTO(HallOfFameODTO hallOfFame);
}
