package org.gycoding.accounts.application.mapper;

import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.MetadataIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserServiceMapper {
    ProfileODTO toODTO(ProfileMO profile);

    @Mapping(target = "isFriend", source = "isFriend")
    ProfileODTO toODTO(ProfileMO profile, Boolean isFriend);

    ProfileMO toMO(ProfileIDTO profile);

    @Mapping(target = "picture", source = "picture")
    ProfileMO toMO(ProfileIDTO profile, String picture);

    PictureODTO toODTO(PictureMO picture);

    MetadataODTO toODTO(MetadataMO metadata);

    MetadataMO toMO(MetadataIDTO metadata);
}
