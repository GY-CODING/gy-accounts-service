package org.gycoding.accounts.application.service.auth;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.exceptions.model.APIException;

public interface AuthService {
    TokenHolder login(UserIDTO user) throws APIException;
    CreatedUser signUp(UserIDTO user) throws APIException;

    String googleAuth() throws APIException;
    TokenHolder handleGoogleResponse(String code) throws APIException;
}