package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.gycoding.accounts.application.service.gyclient.ClientRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthService implements AuthRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private ClientRepository clientRepository = null;

    @Autowired
    private UsernameMongoService usernameMongoService = null;

    @Autowired
    private PictureMongoService pictureMongoService = null;

    @Value("${gy.accounts.picture.url}")
    private String GY_ACCOUNTS_PICTURE_URL;

    @Override
    public TokenHolder login(String email, String password) throws APIException {
        try {
            return authFacade.login(email, password);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.INVALID_LOGIN.getCode(),
                    AccountsAPIError.INVALID_LOGIN.getMessage(),
                    AccountsAPIError.INVALID_LOGIN.getStatus()
            );
        }
    }

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

        try {
            authFacade.updateUsername(userId, username);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.USERNAME_NOT_SAVED.getCode(),
                    AccountsAPIError.USERNAME_NOT_SAVED.getMessage(),
                    AccountsAPIError.USERNAME_NOT_SAVED.getStatus()
            );
        }

        return savedUsername;
    }

    @Override
    public EntityPicture updatePicture(String userId, MultipartFile picture) throws APIException {
        try {
            final var savedPicture = pictureMongoService.save(
                    EntityPicture.builder()
                            .name(userId + "-pfp")
                            .contentType(picture.getContentType())
                            .picture(new Binary(BsonBinarySubType.BINARY, picture.getBytes()))
                            .build()
            );

            authFacade.updatePicture(userId, GY_ACCOUNTS_PICTURE_URL + userId);

            return savedPicture;
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
    public void updateEmail(String userId, String email) throws APIException {
        try {
            authFacade.updateEmail(userId, email);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.COULD_NOT_UPDATE_EMAIL.getCode(),
                    AccountsAPIError.COULD_NOT_UPDATE_EMAIL.getMessage(),
                    AccountsAPIError.COULD_NOT_UPDATE_EMAIL.getStatus()
            );
        }
    }

    @Override
    public void updatePassword(String userId, String password) throws APIException {
        try {
            authFacade.updatePassword(userId, password);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.COULD_NOT_UPDATE_PASSWORD.getCode(),
                    AccountsAPIError.COULD_NOT_UPDATE_PASSWORD.getMessage(),
                    AccountsAPIError.COULD_NOT_UPDATE_PASSWORD.getStatus()
            );
        }
    }

    @Override
    public CreatedUser signUp(String email, String username, String password) throws APIException {
        try {
            return authFacade.signUp(email, username, password);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.INVALID_SIGNUP.getCode(),
                    AccountsAPIError.INVALID_SIGNUP.getMessage(),
                    AccountsAPIError.INVALID_SIGNUP.getStatus()
            );
        }
    }

    @Override
    public String googleAuth() throws APIException {
        try {
            return authFacade.googleAuth();
        } catch(Exception e) {
            throw new APIException(
                    AccountsAPIError.AUTH_ERROR.getCode(),
                    AccountsAPIError.AUTH_ERROR.getMessage(),
                    AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws APIException {
        try {
            return authFacade.handleGoogleResponse(code);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.AUTH_ERROR.getCode(),
                    AccountsAPIError.AUTH_ERROR.getMessage(),
                    AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }
}
