package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.application.service.gyclient.ClientRepository;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
import org.gycoding.accounts.domain.entities.metadata.UserMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.FriendsMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.ChatMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthService implements AuthRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private ClientRepository clientRepository = null;

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

    @Override
    public void refreshMetadata(String userId) throws APIException {
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
                    .image("null")                                      // TODO. Insert random image for profile pictures.
                    .roles(List.of(GYCODINGRoles.COMMON))
                    .gyMessagesMetadata(defaultGYMessagesMetadata)
                    .gyClientMetadata(defaultGYClientMetadata)
                    .build();

            if(oldMetadata != null) {
                newMetadata.setUsername(oldMetadata.getOrDefault("username", newMetadata.getUsername()).toString());
                newMetadata.setRoles((List<GYCODINGRoles>) oldMetadata.getOrDefault("roles", List.of(GYCODINGRoles.COMMON)));
                newMetadata.setGyMessagesMetadata((GYMessagesMetadata) oldMetadata.getOrDefault("gyMessages", defaultGYMessagesMetadata));
                newMetadata.setGyClientMetadata((GYClientMetadata) oldMetadata.getOrDefault("gyClient", defaultGYClientMetadata));
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

    @Override
    public void setMetadata(
            String userId,
            UserMetadata userMetadata
    ) throws APIException {
        try {
            var oldMetadata = authFacade.getMetadata(userId);

            if(userMetadata == null) {
                throw new APIException(
                        AccountsAPIError.BAD_REQUEST.getCode(),
                        AccountsAPIError.BAD_REQUEST.getMessage(),
                        AccountsAPIError.BAD_REQUEST.getStatus()
                );
            }

            if(oldMetadata == null) {
                this.refreshMetadata(userId);
                oldMetadata = authFacade.getMetadata(userId);
            }

            if (userMetadata.getGyMessagesMetadata() != null) {
                userMetadata.setGyMessagesMetadata(
                        GYMessagesMetadata.builder()
                        .chats(userMetadata.getGyMessagesMetadata().chats() != null ? userMetadata.getGyMessagesMetadata().chats() : (List<ChatMetadata>) ((HashMap<String, Object>) oldMetadata.get("gyMessages")).get("chats"))
                        .build()
                );
            } else {
                userMetadata.setGyMessagesMetadata(
                        GYMessagesMetadata.builder()
                        .chats(List.of())
                        .build()
                );
            }

            if (userMetadata.getGyClientMetadata() != null) {
                userMetadata.setGyClientMetadata(
                        GYClientMetadata.builder()
                        .title(userMetadata.getGyClientMetadata().title() != null ? userMetadata.getGyClientMetadata().title() : (String) ((HashMap<String, Object>) oldMetadata.get("gyClient")).get("title"))
                        .friends(userMetadata.getGyClientMetadata().friends() != null ? userMetadata.getGyClientMetadata().friends() : (List<FriendsMetadata>) ((HashMap<String, Object>) oldMetadata.get("gyClient")).get("friends"))
                        .build()
                );
            } else {
                userMetadata.setGyClientMetadata(
                        GYClientMetadata.builder()
                        .title("null")
                        .friends(List.of())
                        .build()
                );
            }

            userMetadata.setUsername(oldMetadata.getOrDefault("username", userMetadata.getUsername()).toString());
            userMetadata.setRoles((List<GYCODINGRoles>) oldMetadata.getOrDefault("roles", List.of(GYCODINGRoles.COMMON)));

            authFacade.setMetadata(userId, userMetadata.toMap(), Boolean.TRUE);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.METADATA_NOT_UPDATED.getCode(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getMessage(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getStatus()
            );
        }
    }
}
