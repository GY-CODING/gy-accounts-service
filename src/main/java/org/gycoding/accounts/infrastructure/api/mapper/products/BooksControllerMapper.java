package org.gycoding.accounts.infrastructure.api.mapper.products;

import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.books.FriendRequestRSDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BooksControllerMapper {
    FriendRequestRSDTO toRSDTO(FriendRequestODTO friendRequest);
}
