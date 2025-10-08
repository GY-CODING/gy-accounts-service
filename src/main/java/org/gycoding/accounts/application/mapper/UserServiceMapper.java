package org.gycoding.accounts.application.mapper;

import com.auth0.json.mgmt.users.User;
import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.shared.AccountRoles;
import org.gycoding.accounts.shared.utils.Base64Utils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class, AccountRoles.class, Base64Utils.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserServiceMapper {
    ProfileODTO toODTO(ProfileMO profile);

    ProfileMO toMO(ProfileIDTO profile);

    @Mapping(target = "picture", source = "picture")
    ProfileMO toMO(ProfileIDTO profile, String picture);

    PictureODTO toODTO(PictureMO picture);

    MetadataODTO toODTO(MetadataMO metadata);

    default MetadataMO toDefaultMO(String userId, User user, String pictureURL) {
        return MetadataMO.builder()
                .userId(userId)
                .profile(
                        ProfileMO.builder()
                                .id(UUID.randomUUID())
                                .roles(List.of(AccountRoles.COMMON))
                                .apiKey(Base64Utils.generateApiKey())
                                .username(user.getName())
                                .phoneNumber(user.getPhoneNumber())
                                .email(user.getEmail())
                                .picture(pictureURL)
                                .build()
                )
                .build();
    }
}
