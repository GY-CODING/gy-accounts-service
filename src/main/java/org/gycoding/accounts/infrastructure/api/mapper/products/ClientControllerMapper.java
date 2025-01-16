package org.gycoding.accounts.infrastructure.api.mapper.products;

import org.gycoding.accounts.application.dto.out.user.metadata.gyclient.GYClientMetadataODTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gyclient.GYClientMetadataRSDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientControllerMapper {
    GYClientMetadataRSDTO toRSDTO(GYClientMetadataODTO profile);
}
