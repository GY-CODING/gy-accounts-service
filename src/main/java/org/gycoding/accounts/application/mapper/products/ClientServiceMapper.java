package org.gycoding.accounts.application.mapper.products;

import org.gycoding.accounts.application.dto.out.user.metadata.gyclient.GYClientMetadataODTO;
import org.gycoding.accounts.domain.model.user.metadata.gyclient.GYClientMetadataMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientServiceMapper {
    GYClientMetadataODTO toODTO(GYClientMetadataMO profile);
}
