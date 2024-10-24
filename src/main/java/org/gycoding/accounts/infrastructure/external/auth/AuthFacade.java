package org.gycoding.accounts.infrastructure.external.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.entities.metadata.gyclient.FriendsMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthFacade {
    TokenHolder login(String email, String password) throws Auth0Exception;
    CreatedUser signUp(String email, String username, String password) throws Auth0Exception;

    String googleAuth();
    TokenHolder handleGoogleResponse(String code) throws Auth0Exception;

    Map<String, Object> getMetadata(String userId) throws Auth0Exception;
    void setMetadata(String userId, Map<String, Object> metadata, Boolean isReset) throws Auth0Exception;

    GYClientMetadata getClientMetadata(String userId) throws Auth0Exception;

    String decode(String token) throws APIException;
}
