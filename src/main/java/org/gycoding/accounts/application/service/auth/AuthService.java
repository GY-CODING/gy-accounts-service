package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.application.service.username.UsernameRepository;
import org.gycoding.accounts.application.service.username.UsernameService;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
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
    private UsernameRepository usernameRepository = null;

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
            final var newMetadata = new HashMap<String, Object>();

            var defaultGYMessagesMetadata = GYMessagesMetadata.builder()
                    .chats(List.of())
                    .build();

            var defaultGYClientMetadata = GYClientMetadata.builder()
                    .username("null")
                    .title("null")
                    .friends(List.of())
                    .build();

            if(oldMetadata != null) {
                newMetadata.put("role", oldMetadata.getOrDefault("role", GYCODINGRoles.COMMON));
                newMetadata.put("gyMessages", oldMetadata.getOrDefault("gyMessages", defaultGYMessagesMetadata.toMap()));
                newMetadata.put("gyClient", oldMetadata.getOrDefault("gyClient", defaultGYClientMetadata));
            } else {
                newMetadata.put("role", GYCODINGRoles.COMMON);
                newMetadata.put("gyMessages", defaultGYMessagesMetadata);
                newMetadata.put("gyClient", defaultGYClientMetadata);
            }

            authFacade.setMetadata(userId, newMetadata, Boolean.TRUE);
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
            GYMessagesMetadata messagesMetadata,
            GYClientMetadata clientMetadata
    ) throws APIException {
        try {
            final var oldMetadata = authFacade.getMetadata(userId);
            final var newMetadata = new HashMap<String, Object>();

            if(messagesMetadata == null && clientMetadata == null) {
                throw new APIException(
                        AccountsAPIError.BAD_REQUEST.getCode(),
                        AccountsAPIError.BAD_REQUEST.getMessage(),
                        AccountsAPIError.BAD_REQUEST.getStatus()
                );
            }

                if (messagesMetadata != null) {
                    messagesMetadata = GYMessagesMetadata.builder()
                            .chats(messagesMetadata.chats() != null ? messagesMetadata.chats() : (List<ChatMetadata>) ((HashMap<String, Object>) oldMetadata.get("gyMessages")).get("chats"))
                            .build();
                } else {
                    messagesMetadata = GYMessagesMetadata.builder()
                            .chats((List<ChatMetadata>) ((HashMap<String, Object>) oldMetadata.get("gyMessages")).get("chats"))
                            .build();
                }

                if (clientMetadata != null) {
                    clientMetadata = GYClientMetadata.builder()
                            .username(clientMetadata.username() != null ? clientMetadata.username() : (String) ((HashMap<String, Object>) oldMetadata.get("gyClient")).get("username"))
                            .title(clientMetadata.title() != null ? clientMetadata.title() : (String) ((HashMap<String, Object>) oldMetadata.get("gyClient")).get("title"))
                            .friends(clientMetadata.friends() != null ? clientMetadata.friends() : (List<FriendsMetadata>) ((HashMap<String, Object>) oldMetadata.get("gyClient")).get("friends"))
                            .build();
                } else {
                    clientMetadata = GYClientMetadata.builder()
                            .username((String) ((HashMap<String, Object>) oldMetadata.get("gyClient")).get("username"))
                            .title((String) ((HashMap<String, Object>) oldMetadata.get("gyClient")).get("title"))
                            .friends((List<FriendsMetadata>) ((HashMap<String, Object>) oldMetadata.get("gyClient")).get("friends"))
                            .build();
                }

                newMetadata.put("role", oldMetadata.getOrDefault("role", GYCODINGRoles.COMMON));
                newMetadata.put("gyMessages", messagesMetadata);
                newMetadata.put("gyClient", clientMetadata);

            authFacade.setMetadata(userId, newMetadata, Boolean.TRUE);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.METADATA_NOT_UPDATED.getCode(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getMessage(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getStatus()
            );
        }
    }
}
