package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.accounts.application.mapper.AuthServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthFacade authFacade = null;

    @Qualifier("authServiceMapperImpl")
    @Autowired
    private AuthServiceMapper mapper = null;

    @Override
    public TokenHolder login(UserIDTO user) throws APIException {
        try {
            return authFacade.login(mapper.toMO(user));
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.INVALID_LOGIN.getCode(),
                    AccountsAPIError.INVALID_LOGIN.getMessage(),
                    AccountsAPIError.INVALID_LOGIN.getStatus()
            );
        }
    }

    @Override
    public CreatedUser signUp(UserIDTO user) throws APIException {
        try {
            return authFacade.signUp(mapper.toMO(user));
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.INVALID_SIGNUP.getCode(),
                    AccountsAPIError.INVALID_SIGNUP.getMessage(),
                    AccountsAPIError.INVALID_SIGNUP.getStatus()
            );
        }
    }

    @Override
    public String googleAuth() throws APIException {
        try {
            return authFacade.googleAuth();
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.AUTH_ERROR.getCode(),
                    AccountsAPIError.AUTH_ERROR.getMessage(),
                    AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws APIException {
        try {
            return authFacade.handleGoogleResponse(code);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.AUTH_ERROR.getCode(),
                    AccountsAPIError.AUTH_ERROR.getMessage(),
                    AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }
}
