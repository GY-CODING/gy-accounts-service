package org.gycoding.accounts.infrastructure.external.database.mapper;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestMO;
import org.gycoding.accounts.infrastructure.external.database.model.FriendRequestEntity;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendRequestDatabaseMapper {
    FriendRequestMO toMO(FriendRequestEntity friendRequest);

    FriendRequestEntity toEntity(FriendRequestMO friendRequest);
}
