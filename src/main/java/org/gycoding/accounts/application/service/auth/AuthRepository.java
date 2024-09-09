package org.gycoding.accounts.application.service.auth;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.exceptions.model.APIException;

public interface AuthRepository {
    TokenHolder login(String email, String password) throws APIException;
    CreatedUser signUp(String email, String username, String password) throws APIException;
    String googleAuth() throws APIException;
    TokenHolder handleGoogleResponse(String code) throws APIException;
    void setMetadata(String token, Boolean isReset) throws APIException;
    void refreshMetadata(String token) throws APIException;
}