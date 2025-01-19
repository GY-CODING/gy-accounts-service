package org.gycoding.accounts.application.mapper;

import org.gycoding.accounts.application.dto.in.user.ProfileIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.MetadataIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.ProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserServiceMapper {
    ProfileODTO toODTO(ProfileMO profile);

    ProfileMO toMO(ProfileIDTO profile);

    MetadataODTO toODTO(MetadataMO profile);

    MetadataMO toMO(MetadataIDTO profile);

    PictureODTO toODTO(PictureMO profile);
}