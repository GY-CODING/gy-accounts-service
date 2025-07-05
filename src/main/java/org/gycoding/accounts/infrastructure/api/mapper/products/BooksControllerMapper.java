package org.gycoding.accounts.infrastructure.api.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.gymessages.ChatODTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages.ChatRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.books.FriendRequestRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gymessages.ChatRSDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BooksControllerMapper {
    FriendRequestRSDTO toRSDTO(FriendRequestODTO friendRequest);
}
