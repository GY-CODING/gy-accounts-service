package org.gycoding.accounts.infrastructure.api.mapper;

import org.gycoding.accounts.application.dto.in.user.metadata.MetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.MetadataRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.ProfileRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.MetadataRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gymessages.ProfileRSDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserControllerMapper {
    ProfileRSDTO toRSDTO(ProfileODTO profile);

    ProfileIDTO toIDTO(ProfileRQDTO profile);

    MetadataRSDTO toRSDTO(MetadataODTO metadata);

    MetadataIDTO toIDTO(MetadataRQDTO metadata);
}
