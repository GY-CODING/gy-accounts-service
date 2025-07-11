package org.gycoding.accounts.infrastructure.external.database.mapper;

import kotlin.Metadata;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.gycoding.accounts.infrastructure.external.database.model.metadata.MetadataEntity;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MetadataRepositoryMapper {
    @Mapping(target = "profile.id", expression = "java(toUUID(profileEntity.getId()))")
    MetadataMO toMO(MetadataEntity metadata);

    MetadataEntity toEntity(MetadataMO metadata);

    @Mapping(target = "mongoId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MetadataEntity toUpdatedEntity(@MappingTarget MetadataEntity persistedMetadata, MetadataMO metadata);

    default UUID toUUID(String id) {
        return id == null ? null : UUID.fromString(id);
    }

    default String toString(UUID id) {
        return id == null ? null : id.toString();
    }
}
