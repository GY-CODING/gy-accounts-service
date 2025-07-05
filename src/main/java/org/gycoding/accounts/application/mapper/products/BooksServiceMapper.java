package org.gycoding.accounts.application.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.books.BooksMetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.BooksMetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.gymessages.ChatODTO;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.ChatMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
public interface BooksServiceMapper {
    BooksMetadataODTO toODTO(BooksMetadataMO booksMetadata);
    BooksMetadataMO toMO(BooksMetadataIDTO booksMetadata);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "from", source = "userId")
    @Mapping(target = "to", source = "toUserId")
    FriendRequestMO toMO(String userId, String toUserId);

    FriendRequestODTO toODTO(FriendRequestMO friendRequestMO);
}
