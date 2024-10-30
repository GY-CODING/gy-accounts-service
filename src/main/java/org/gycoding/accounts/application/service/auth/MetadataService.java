package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.domain.entities.database.EntityUsername;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
import org.gycoding.accounts.domain.entities.metadata.UserMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.FriendsMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.ChatMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;
import org.gycoding.accounts.domain.entities.model.auth.MultipartFileImpl;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.accounts.infrastructure.external.database.service.PictureMongoService;
import org.gycoding.accounts.infrastructure.external.database.service.UsernameMongoService;
import org.gycoding.accounts.infrastructure.external.unirest.UnirestFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MetadataService implements MetadataRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private UsernameMongoService usernameMongoService = null;

    @Autowired
    private PictureMongoService pictureMongoService = null;

    @Value("${gy.accounts.defaultpicture.url}")
    private String DEFAULT_PICTURE_URL;

    @Override
    public EntityUsername updateUsername(String userId, String username) throws APIException {
        int usernameCount = 0;
        String originalUsername = username;

        while(usernameMongoService.existsByUsername(username)) {
            usernameCount++;
            username = originalUsername + usernameCount;
        }

        final var entityUsername = EntityUsername.builder()
                .userId(userId)
                .username(username)
                .build();

        final var savedUsername = usernameMongoService.save(entityUsername);

        this.updateMetadata(
                userId,
                Optional.of(
                        UserMetadata.builder()
                                .username(savedUsername.username())
                                .roles(List.of(GYCODINGRoles.COMMON))
                                .gyClientMetadata(
                                        GYClientMetadata.builder()
                                                .title("null")
                                                .friends(List.of())
                                                .build()
                                )
                                .gyMessagesMetadata(
                                        GYMessagesMetadata.builder()
                                                .chats(List.of())
                                                .build()
                                )
                                .build()
                )
        );

        return savedUsername;
    }

    @Override
    public EntityPicture updatePicture(String userId) throws APIException {
        userId = userId.replace("google-oauth2|", "");
        userId = userId.replace("auth0|", "");

        final Double randomCount = Math.random() * 354;

        try {
            return pictureMongoService.save(
                    EntityPicture.builder()
                            .name(userId + "-pfp")
                            .contentType("image/jpg")
                            .picture(new Binary(BsonBinarySubType.BINARY, UnirestFacade.get(String.format(DEFAULT_PICTURE_URL, randomCount.intValue())).getBody().getBytes()))
                            .build()
            );
        } catch(Exception e) {
            throw new APIException(
                    AccountsAPIError.PICTURE_NOT_SAVED.getCode(),
                    AccountsAPIError.PICTURE_NOT_SAVED.getMessage(),
                    AccountsAPIError.PICTURE_NOT_SAVED.getStatus()
            );
        }
    }

    @Override
    public EntityPicture updatePicture(String userId, MultipartFile picture) throws APIException {
        userId = userId.replace("google-oauth2|", "");
        userId = userId.replace("auth0|", "");

        try {
            return pictureMongoService.save(
                    EntityPicture.builder()
                            .name(userId + "-pfp")
                            .contentType(picture.getContentType())
                            .picture(new Binary(BsonBinarySubType.BINARY, picture.getBytes()))
                            .build()
            );
        } catch(Exception e) {
            throw new APIException(
                    AccountsAPIError.PICTURE_NOT_SAVED.getCode(),
                    AccountsAPIError.PICTURE_NOT_SAVED.getMessage(),
                    AccountsAPIError.PICTURE_NOT_SAVED.getStatus()
            );
        }
    }

    @Override
    public EntityPicture getPicture(String userId) throws APIException {
        userId = userId.replace("google-oauth2|", "");
        userId = userId.replace("auth0|", "");

        try {
            return pictureMongoService.getPicture(userId + "-pfp");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new APIException(
                    AccountsAPIError.PICTURE_NOT_FOUND.getCode(),
                    AccountsAPIError.PICTURE_NOT_FOUND.getMessage(),
                    AccountsAPIError.PICTURE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public void updateMetadata(String userId, Optional<UserMetadata> metadata) throws APIException {
        try {
            final var oldMetadata = authFacade.getMetadata(userId);
            UserMetadata newMetadata = null;

            var defaultGYMessagesMetadata = GYMessagesMetadata.builder()
                    .chats(List.of())
                    .build();

            var defaultGYClientMetadata = GYClientMetadata.builder()
                    .title("null")
                    .friends(List.of())
                    .build();

            newMetadata = metadata.orElseGet(() -> UserMetadata.builder()
                    .username("null")
                    .roles(List.of(GYCODINGRoles.COMMON))
                    .gyMessagesMetadata(defaultGYMessagesMetadata)
                    .gyClientMetadata(defaultGYClientMetadata)
                    .build());



            if(oldMetadata != null) {
                newMetadata.setUsername(oldMetadata.getOrDefault("username", newMetadata.getUsername()).toString());
                newMetadata.setRoles((List<GYCODINGRoles>) oldMetadata.getOrDefault("roles", List.of(GYCODINGRoles.COMMON)));
                newMetadata.setGyMessagesMetadata(
                        GYMessagesMetadata.builder()
                                .chats((List<ChatMetadata>) ((HashMap<String, Object>) oldMetadata.get("gyMessages")).getOrDefault("chats", newMetadata.getGyMessagesMetadata().chats()))
                                .build()
                );
                newMetadata.setGyClientMetadata(
                        GYClientMetadata.builder()
                                .title((String) ((HashMap<String, Object>) oldMetadata.get("gyClient")).getOrDefault("title", newMetadata.getGyClientMetadata().title()))
                                .friends((List<FriendsMetadata>) ((HashMap<String, Object>) oldMetadata.get("gyClient")).getOrDefault("friends", newMetadata.getGyClientMetadata().friends()))
                                .build());
            }

            authFacade.setMetadata(userId, newMetadata.toMap(), Boolean.TRUE);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.METADATA_NOT_UPDATED.getCode(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getMessage(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getStatus()
            );
        }
    }
}
