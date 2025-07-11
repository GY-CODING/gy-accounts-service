package org.gycoding.accounts.infrastructure.external.database.mapper;

import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestMO;
import org.gycoding.accounts.infrastructure.external.database.model.FriendRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendRequestDatabaseMapper {
    FriendRequestMO toMO(FriendRequestEntity friendRequest);

    FriendRequestEntity toEntity(FriendRequestMO friendRequest);

    default UUID toUUID(String id) {
        return id == null ? null : UUID.fromString(id);
    }

    default String toString(UUID id) {
        return id == null ? null : id.toString();
    }
}
