package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;
import org.gycoding.accounts.domain.enums.ServerStatus;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthService implements AuthRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Override
    public TokenHolder login(String email, String password) throws AccountsAPIException {
        try {
            return authFacade.login(email, password);
        } catch(Auth0Exception e) {
            e.printStackTrace();
            throw new AccountsAPIException(ServerStatus.INVALID_LOGIN);
        }
    }

    @Override
    public CreatedUser signUp(String email, String username, String password) throws AccountsAPIException {
        try {
            return authFacade.signUp(email, username, password);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.INVALID_SIGNUP);
        }
    }

    @Override
    public String googleAuth() throws AccountsAPIException {
        try {
            return authFacade.googleAuth();
        } catch(Exception e) {
            throw new AccountsAPIException(ServerStatus.AUTH_ERROR);
        }
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws AccountsAPIException {
        try {
            return authFacade.handleGoogleResponse(code);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.AUTH_ERROR);
        }
    }

    @Override
    public String decode(String jwt) throws AccountsAPIException {
        try {
            return authFacade.decode(jwt);
        } catch(Exception e) {
            throw new AccountsAPIException(ServerStatus.INVALID_JWT_FORMAT);
        }
    }

    @Override
    public void setMetadata(String userId, Boolean isReset) throws AccountsAPIException {
        final var metadata = new HashMap<String, Object>();
        var gyMessagesMetadata = GYMessagesMetadata.builder()
                .chats(List.of())
                .build();

        try {
            metadata.put("role", GYCODINGRoles.COMMON);
            metadata.put("gyMessages", gyMessagesMetadata.toMap());

            authFacade.setMetadata(String.format("%s", userId), metadata, isReset);
        } catch(Auth0Exception e) {
            e.printStackTrace();
            throw new AccountsAPIException(ServerStatus.METADATA_NOT_UPDATED);
        }
    }
}
