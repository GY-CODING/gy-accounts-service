package org.gycoding.accounts.application.service.auth;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.quasar.exceptions.model.ServiceException;

public interface AuthService {
    TokenHolder login(UserIDTO user) throws ServiceException;
    CreatedUser signUp(UserIDTO user) throws ServiceException;

    String googleAuth() throws ServiceException;
    TokenHolder handleGoogleResponse(String code) throws ServiceException;
}