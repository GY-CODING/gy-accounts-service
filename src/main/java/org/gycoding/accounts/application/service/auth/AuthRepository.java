package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;

public interface AuthRepository {
    TokenHolder login(String email, String password) throws AccountsAPIException;
    CreatedUser signUp(String email, String username, String password) throws AccountsAPIException;
    String googleAuth() throws AccountsAPIException;
    TokenHolder handleGoogleResponse(String code) throws AccountsAPIException;
    String decode(String jwt) throws AccountsAPIException;
}
