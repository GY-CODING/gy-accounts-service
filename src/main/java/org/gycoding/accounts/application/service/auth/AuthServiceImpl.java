package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.accounts.application.mapper.AuthServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsError;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.quasar.exceptions.model.ServiceException;
import org.gycoding.quasar.logs.service.Logger;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthFacade authFacade;
    private final AuthServiceMapper mapper;

    @Override
    public TokenHolder login(UserIDTO user) throws ServiceException {
        try {
            return authFacade.login(mapper.toMO(user));
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while logging in.", user);

            throw new ServiceException(AccountsError.INVALID_LOGIN);
        }
    }

    @Override
    public CreatedUser signUp(UserIDTO user) throws ServiceException {
        try {
            return authFacade.signUp(mapper.toMO(user));
        } catch(Auth0Exception e) {
            Logger.error("An error has occurred while signing up.", user);

            throw new ServiceException(AccountsError.INVALID_SIGNUP);
        }
    }

    @Override
    public String googleAuth() throws ServiceException {
        try {
            return authFacade.googleAuth();
        } catch(Exception e) {
            Logger.error("An unknown error has occurred while retrieving the Google authentication URL.");

            throw new ServiceException(AccountsError.AUTH_ERROR);
        }
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws ServiceException {
        try {
            return authFacade.handleGoogleResponse(code);
        } catch(Auth0Exception e) {
            Logger.error(String.format("An error has occurred on the callback from Google Authentication with code: %s.", code));

            throw new ServiceException(AccountsError.AUTH_ERROR);
        }
    }
}
