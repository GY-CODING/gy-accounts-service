package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;

public interface AuthRepository {
    TokenHolder login(String email, String password) throws Auth0Exception;
    CreatedUser signUp(String email, String username, String password) throws Exception;
    String googleAuth();
    TokenHolder handleGoogleResponse(String code) throws Auth0Exception;
    String decode(String jwt);
}
