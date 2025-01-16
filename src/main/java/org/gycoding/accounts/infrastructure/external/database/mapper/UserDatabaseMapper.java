package org.gycoding.accounts.infrastructure.external.database.mapper;

import org.bson.types.Binary;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDatabaseMapper {
    PictureMO toMO(PictureEntity pictureEntity);

    PictureEntity toEntity(PictureMO pictureMO);
}
