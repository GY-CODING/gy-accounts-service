package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.application.service.achievements.AchievementsRepository;
import org.gycoding.accounts.domain.enums.ServerStatus;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthService implements AuthRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private AchievementsRepository achievementsRepository = null;

    @Override
    public TokenHolder login(String email, String password) throws AccountsAPIException {
        try {
            return authFacade.login(email, password);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.INVALID_LOGIN);
        }
    }

    @Override
    public CreatedUser signUp(String email, String username, String password) throws AccountsAPIException {
        CreatedUser user;
        List<HashMap<String, Object>> achievements;
        final var metadata = new HashMap<String, Object>();

        try {
            user = authFacade.signUp(email, username, password);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.INVALID_SIGNUP);
        }

        try {
            achievements = achievementsRepository.listAchievements().stream().map(achievement -> {
                        final var map = new HashMap<String, Object>();

                        map.put("identifier", achievement.identifier());
                        map.put("unlocked", false);

                        return map;
                    })
                    .toList();
        } catch(Exception e) {
            throw new AccountsAPIException(ServerStatus.ACHIEVEMENTS_NOT_FOUND);
        }

        try {
            metadata.put("achievements", achievements);
            authFacade.updateMetadata(String.format("auth0|%s", user.getUserId()), metadata);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.METADATA_NOT_UPDATED);
        }

        return user;
    }

    @Override
    public String googleAuth() throws AccountsAPIException {
        try {
            return authFacade.googleAuth();
        } catch(Exception e) {
            throw new AccountsAPIException(ServerStatus.AUTH_ERROR);
        }
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws AccountsAPIException {
        try {
            return authFacade.handleGoogleResponse(code);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.AUTH_ERROR);
        }
    }

    @Override
    public String decode(String jwt) throws AccountsAPIException {
        try {
            return authFacade.decode(jwt);
        } catch(Exception e) {
            throw new AccountsAPIException(ServerStatus.INVALID_JWT_FORMAT);
        }
    }
}
