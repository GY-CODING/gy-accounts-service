package org.gycoding.accounts.application.service.user;

import com.auth0.exception.Auth0Exception;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.gycoding.accounts.application.dto.in.user.ProfileIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.MetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.gyclient.FriendMetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.gyclient.GYClientMetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatMetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.GYMessagesMetadataIDTO;
import org.gycoding.accounts.application.dto.out.user.PictureODTO;
import org.gycoding.accounts.application.dto.out.user.ProfileODTO;
import org.gycoding.accounts.application.mapper.UserServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.gyclient.FriendMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.gyclient.GYClientMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.ChatMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.GYMessagesMetadataMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.gycoding.accounts.infrastructure.external.database.service.PictureMongoService;
import org.gycoding.accounts.infrastructure.external.utils.FileUtils;
import org.gycoding.accounts.shared.GYCODINGRoles;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthFacade authFacade = null;

    @Qualifier("userServiceMapperImpl")
    @Autowired
    private UserServiceMapper mapper = null;

    @Autowired
    private PictureMongoService pictureMongoService = null;

    @Value("${gy.accounts.picture.url}")
    private String GY_ACCOUNTS_PICTURE_URL;

    @Override
    public ProfileODTO getProfile(String userId) throws APIException {
        try {
            return mapper.toODTO(authFacade.getProfile(userId));
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public ProfileODTO updateProfile(String userId, ProfileIDTO profile) throws APIException {
        try {
            return mapper.toODTO(authFacade.updateProfile(userId, mapper.toMO(profile)));
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public String getUsername(String userId) throws APIException {
        try {
            return authFacade.getUsername(userId);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public String updateUsername(String userId, String username) throws APIException {
        try {
            return authFacade.updateUsername(userId, username);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public String getEmail(String userId) throws APIException {
        try {
            return authFacade.getEmail(userId);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public String updateEmail(String userId, String email) throws APIException {
        try {
            return authFacade.updateEmail(userId, email);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public PictureODTO getPicture(String userId) throws APIException {
        try {
            userId = userId.replace("auth0|", "");
            userId = userId.replace("google-oauth2|", "");

            return mapper.toODTO(pictureMongoService.getPicture(userId + "-pfp"));
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public PictureODTO updatePicture(String userId, MultipartFile picture) throws APIException {
        try {
            var formattedUserId = userId;
            formattedUserId = formattedUserId.replace("auth0|", "");
            formattedUserId = formattedUserId.replace("google-oauth2|", "");

            final var savedPicture = pictureMongoService.save(
                    PictureMO.builder()
                            .name(formattedUserId + "-pfp")
                            .contentType(picture.getContentType())
                            .picture(new Binary(BsonBinarySubType.BINARY, picture.getBytes()))
                            .build()
            );

            authFacade.updatePicture(userId, GY_ACCOUNTS_PICTURE_URL + formattedUserId);

            return mapper.toODTO(savedPicture);
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public String getPhoneNumber(String userId) throws APIException {
        try {
            return authFacade.getPhoneNumber(userId);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public String updatePhoneNumber(String userId, String phoneNumber) throws APIException {
        try {
            return authFacade.updatePhoneNumber(userId, phoneNumber);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public void updatePassword(String userId, String password) throws APIException {
        try {
            authFacade.updatePassword(userId, password);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }

    @Override
    public Object getMetadata(String userId) throws APIException {
        try {
            final var oldMetadata = authFacade.getMetadata(userId);

            return null;
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public void updateMetadata(String userId, Optional<MetadataIDTO> metadata) throws APIException {
        try {
            final var oldMetadata = authFacade.getMetadata(userId);
            MetadataIDTO newMetadata = null;

            var defaultGYMessagesMetadata = GYMessagesMetadataIDTO.builder()
                    .chats(List.of())
                    .build();

            var defaultGYClientMetadata = GYClientMetadataIDTO.builder()
                    .title("null")
                    .friends(List.of())
                    .build();

            if (metadata.isPresent()) {
                newMetadata = metadata.get();

                if(oldMetadata != null) {
                    newMetadata.setRoles(newMetadata.getRoles() != null ? newMetadata.getRoles() : ((List<String>) oldMetadata.getOrDefault("roles", List.of(GYCODINGRoles.COMMON.toString()))).stream().map(GYCODINGRoles::fromString).toList());
                    newMetadata.setGyMessages(
                            newMetadata.getGyMessages() != null ? newMetadata.getGyMessages() :
                                    GYMessagesMetadataIDTO.builder()
                                        .chats((List<ChatMetadataIDTO>) ((HashMap<String, Object>) oldMetadata.get("gyMessages")).getOrDefault("chats", defaultGYMessagesMetadata.chats()))
                                        .build()
                    );
                    newMetadata.setGyClient(
                            newMetadata.getGyClient() != null ? newMetadata.getGyClient() :
                                    GYClientMetadataIDTO.builder()
                                        .title((String) ((HashMap<String, Object>) oldMetadata.get("gyClient")).getOrDefault("title", defaultGYClientMetadata.title()))
                                        .friends((List<FriendMetadataIDTO>) ((HashMap<String, Object>) oldMetadata.get("gyClient")).getOrDefault("friends", defaultGYClientMetadata.friends()))
                                        .build());
                }
            } else {
                newMetadata = MetadataIDTO.builder()
                        .roles(List.of(GYCODINGRoles.COMMON))
                        .gyMessages(defaultGYMessagesMetadata)
                        .gyClient(defaultGYClientMetadata)
                        .build();

                if(oldMetadata != null) {
                    newMetadata.setRoles(((List<String>) oldMetadata.getOrDefault("roles", List.of(GYCODINGRoles.COMMON))).stream().map(GYCODINGRoles::fromString).toList());
                    newMetadata.setGyMessages(
                            GYMessagesMetadataIDTO.builder()
                                    .chats((List<ChatMetadataIDTO>) ((HashMap<String, Object>) oldMetadata.get("gyMessages")).getOrDefault("chats", newMetadata.getGyMessages().chats()))
                                    .build()
                    );
                    newMetadata.setGyClient(
                            GYClientMetadataIDTO.builder()
                                    .title((String) ((HashMap<String, Object>) oldMetadata.get("gyClient")).getOrDefault("title", newMetadata.getGyClient().title()))
                                    .friends((List<FriendMetadataIDTO>) ((HashMap<String, Object>) oldMetadata.get("gyClient")).getOrDefault("friends", newMetadata.getGyClient().friends()))
                                    .build());
                }
            }

            authFacade.setMetadata(userId, newMetadata.toMap(), Boolean.TRUE);
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getCode(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_MODIFIED.getStatus()
            );
        }
    }
}
