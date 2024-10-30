package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.gycoding.accounts.domain.entities.database.EntityUsername;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
import org.gycoding.accounts.domain.entities.metadata.UserMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.FriendsMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.ChatMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.accounts.infrastructure.external.database.service.PictureMongoService;
import org.gycoding.accounts.infrastructure.external.database.service.UsernameMongoService;
import org.gycoding.accounts.infrastructure.external.unirest.UnirestFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public class MetadataService implements MetadataRepository {
    @Autowired
    private AuthFacade authFacade = null;

    private final UnirestFacade unirestFacade = new UnirestFacade();

    @Autowired
    private UsernameMongoService usernameMongoService = null;

    @Autowired
    private PictureMongoService pictureMongoService = null;

    @Override
    public EntityUsername saveUsername(String userID, String username) throws APIException {
        int usernameCount = 0;
        String originalUsername = username;

        while(usernameMongoService.existsByUsername(username)) {
            usernameCount++;
            username = originalUsername + usernameCount;
        }

        final var entityUsername = EntityUsername.builder()
                .userId(userID)
                .username(username)
                .build();

        return usernameMongoService.save(entityUsername);
    }

    @Override
    public EntityPicture savePicture(String userId) throws APIException {
        try {
            final var pictureContent = unirestFacade.get("https://raw.githubusercontent.com/GY-CODING/img-repo/refs/heads/main/gy-accounts/default-profile-pictures/dddepth-001.jpg").getBody();
            final MultipartFile picture = null;
            return pictureMongoService.save(userId, picture);
        } catch(Exception e) {
            throw new APIException(
                    AccountsAPIError.PICTURE_NOT_SAVED.getCode(),
                    AccountsAPIError.PICTURE_NOT_SAVED.getMessage(),
                    AccountsAPIError.PICTURE_NOT_SAVED.getStatus()
            );
        }
    }

    @Override
    public EntityPicture savePicture(String userId, MultipartFile picture) throws APIException {
        try {
            return pictureMongoService.save(userId, picture);
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
        try {
            return pictureMongoService.getPicture(userId);
        } catch(Exception e) {
            throw new APIException(
                    AccountsAPIError.PICTURE_NOT_FOUND.getCode(),
                    AccountsAPIError.PICTURE_NOT_FOUND.getMessage(),
                    AccountsAPIError.PICTURE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public void setupMetadata(String userId) throws APIException {
        try {
            final var oldMetadata = authFacade.getMetadata(userId);

            var defaultGYMessagesMetadata = GYMessagesMetadata.builder()
                    .chats(List.of())
                    .build();

            var defaultGYClientMetadata = GYClientMetadata.builder()
                    .title("null")
                    .friends(List.of())
                    .build();

            var newMetadata = UserMetadata.builder()
                    .username("null")
                    .picture("https://api.gycoding.com/accounts/auth/pictures/get?userId=" + userId)
                    .roles(List.of(GYCODINGRoles.COMMON))
                    .gyMessagesMetadata(defaultGYMessagesMetadata)
                    .gyClientMetadata(defaultGYClientMetadata)
                    .build();

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
