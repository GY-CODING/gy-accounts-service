package org.gycoding.accounts.application.mapper;

import com.auth0.json.mgmt.users.User;
import org.gycoding.accounts.application.dto.in.user.metadata.ProfileIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.MetadataIDTO;
import org.gycoding.accounts.application.dto.out.books.BooksProfileODTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.books.HallOfFameMO;
import org.gycoding.accounts.domain.model.user.metadata.messages.MessagesMetadataMO;
import org.gycoding.accounts.shared.AccountRoles;
import org.gycoding.accounts.shared.utils.Base64Utils;
import org.gycoding.accounts.shared.utils.FileUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

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
                .books(
                        BooksMetadataMO.builder()
                                .friends(List.of())
                                .biography(String.format("Hi, I'm %s. Nice to meet you!", user.getName()))
                                .hallOfFame(
                                        HallOfFameMO.builder()
                                                .books(List.of())
                                                .quote("")
                                                .build()
                                )
                                .activity(List.of())
                                .build()
                )
                .messages(
                        MessagesMetadataMO.builder()
                                .chats(List.of())
                                .build()
                )
                .build();
    }
}
