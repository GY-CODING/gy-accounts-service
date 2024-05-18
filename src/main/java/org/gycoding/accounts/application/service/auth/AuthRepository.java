package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;

public interface AuthRepository {
    public TokenHolder login(String email, String password) throws Auth0Exception;
    public CreatedUser signUp(String email, String username, String password) throws Auth0Exception;
    public String googleAuth();
    public TokenHolder handleGoogleResponse(String code) throws Auth0Exception;
}
