package org.gycoding.accounts.application.service.auth;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.springexceptions.model.APIException;

public interface AuthRepository {
    TokenHolder login(String email, String password) throws APIException;
    CreatedUser signUp(String email, String username, String password) throws APIException;
    String googleAuth() throws APIException;
    TokenHolder handleGoogleResponse(String code) throws APIException;
    String decode(String jwt) throws APIException;
    void setMetadata(String userId, Boolean isReset) throws APIException;
}
