package org.gycoding.accounts.application.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.books.BooksMetadataIDTO;
import org.gycoding.accounts.application.dto.out.books.BooksProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.BooksMetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
public interface BooksServiceMapper {
    BooksMetadataODTO toODTO(BooksMetadataMO booksMetadata);

    BooksMetadataMO toMO(BooksMetadataIDTO booksMetadata);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "from", source = "from")
    @Mapping(target = "to", source = "to")
    FriendRequestMO toMO(UUID from, UUID to);

    FriendRequestODTO toODTO(FriendRequestMO friendRequestMO);

    @Mapping(target = "biography", source = "biography")
    BooksProfileODTO toODTO(ProfileMO profile, String biography);

    @Mapping(target = "isFriend", source = "isFriend")
    @Mapping(target = "biography", source = "biography")
    BooksProfileODTO toODTO(ProfileMO profile, String biography, Boolean isFriend);
}
