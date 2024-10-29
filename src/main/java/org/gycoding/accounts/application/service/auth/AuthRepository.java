package org.gycoding.accounts.application.service.auth;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.entities.metadata.UserMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;
import org.gycoding.exceptions.model.APIException;

public interface AuthRepository {
    TokenHolder login(String email, String password) throws APIException;
    CreatedUser signUp(String email, String username, String password) throws APIException;
    String googleAuth() throws APIException;
    TokenHolder handleGoogleResponse(String code) throws APIException;
    void refreshMetadata(String token) throws APIException;
    void setMetadata(String userId, UserMetadata userMetadata) throws APIException;
}