package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Override
    public TokenHolder login(String email, String password) throws Auth0Exception {
        return authFacade.login(email, password);
    }

    @Override
    public CreatedUser signUp(String email, String username, String password) throws Auth0Exception {
        return authFacade.signUp(email, username, password);
    }

    @Override
    public String googleAuth() {
        return authFacade.googleAuth();
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws Auth0Exception {
        return authFacade.handleGoogleResponse(code);
    }
}
