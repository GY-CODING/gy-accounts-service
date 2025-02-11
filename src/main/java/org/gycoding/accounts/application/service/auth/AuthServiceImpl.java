package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import kong.unirest.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.accounts.application.mapper.AuthServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.shared.utils.logger.LogDTO;
import org.gycoding.accounts.shared.utils.logger.LogLevel;
import org.gycoding.accounts.shared.utils.logger.Logger;
import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthFacade authFacade;

    private final AuthServiceMapper mapper;

    @Override
    public TokenHolder login(UserIDTO user) throws APIException {
        try {
            final var authCredentials = authFacade.login(mapper.toMO(user));

            Logger.log(
                    LogDTO.builder()
                            .level(LogLevel.INFO)
                            .message("User has successfully logged in.")
                            .data(JSONObject.stringToValue("{\"token\": \"" + authCredentials.getAccessToken() + "\"}"))
                            .build()
            );

            return authCredentials;
        } catch(Auth0Exception e) {
            Logger.log(
                    LogDTO.builder()
                            .level(LogLevel.ERROR)
                            .message("An error has occurred while logging in.")
                            .data(JSONObject.stringToValue("{\"error\": \"" + e.getMessage() + "\"}"))
                            .build()
            );
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
            Logger.log(
                    LogDTO.builder()
                            .level(LogLevel.ERROR)
                            .message("An error has occurred while signing up.")
                            .data(JSONObject.stringToValue("{\"error\": \"" + e.getMessage() + "\"}"))
                            .build()
            );
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
            Logger.log(
                    LogDTO.builder()
                            .level(LogLevel.ERROR)
                            .message("An unknown error has occurred while retrieving the Google authentication URL.")
                            .data(JSONObject.stringToValue("{\"error\": \"" + e.getMessage() + "\"}"))
                            .build()
            );
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
            Logger.log(
                    LogDTO.builder()
                            .level(LogLevel.ERROR)
                            .message("An error has occurred on the callback from Google Authentication.")
                            .data(JSONObject.stringToValue("{\"error\": \"" + e.getMessage() + "\"}"))
                            .build()
            );
            throw new APIException(
                    AccountsAPIError.AUTH_ERROR.getCode(),
                    AccountsAPIError.AUTH_ERROR.getMessage(),
                    AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }
}
