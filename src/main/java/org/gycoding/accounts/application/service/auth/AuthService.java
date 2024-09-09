package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
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
    public void setMetadata(String token, Boolean isReset) throws APIException {
        final var metadata = new HashMap<String, Object>();
        var defaultGYMessagesMetadata = GYMessagesMetadata.builder()
                .chats(List.of())
                .build();

        var defaultGYClientMetadata = GYClientMetadata.builder()
                .username(null)
                .title(null)
                .friends(List.of())
                .build();

        try {
            metadata.put("role", GYCODINGRoles.COMMON);
            metadata.put("gyMessages", defaultGYMessagesMetadata.toMap());
            metadata.put("gyClient", defaultGYClientMetadata.toMap());

            authFacade.setMetadata(token, metadata, isReset);
        } catch(Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.METADATA_NOT_UPDATED.getCode(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getMessage(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getStatus()
            );
        }
    }

    @Override
    public void refreshMetadata(String token) throws APIException {
        try {
            final var oldMetadata = authFacade.getMetadata(token);
            final var newMetadata = new HashMap<String, Object>();

            var defaultGYMessagesMetadata = GYMessagesMetadata.builder()
                    .chats(List.of())
                    .build();

            var defaultGYClientMetadata = GYClientMetadata.builder()
                    .username(null)
                    .title(null)
                    .friends(List.of())
                    .build();

            newMetadata.put("role", oldMetadata.getOrDefault("role", GYCODINGRoles.COMMON));
            newMetadata.put("gyMessages", oldMetadata.getOrDefault("gyMessages", defaultGYMessagesMetadata.toMap()));
            newMetadata.put("gyClient", oldMetadata.getOrDefault("gyClient", defaultGYClientMetadata));

            authFacade.setMetadata(token, newMetadata, Boolean.TRUE);
            System.out.println("Metadata refreshed");
        } catch(Auth0Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new APIException(
                    AccountsAPIError.METADATA_NOT_UPDATED.getCode(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getMessage(),
                    AccountsAPIError.METADATA_NOT_UPDATED.getStatus()
            );
        }
    }
}
