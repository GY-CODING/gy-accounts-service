package org.gycoding.accounts.application.service.auth;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.application.service.achievements.AchievementsRepository;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.accounts.infrastructure.external.fotg.FOTGFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService implements AuthRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private AchievementsRepository achievementsRepository = null;

    @Override
    public TokenHolder login(String email, String password) throws Auth0Exception {
        return authFacade.login(email, password);
    }

    @Override
    public CreatedUser signUp(String email, String username, String password) throws Auth0Exception {
        final var user          = authFacade.signUp(email, username, password);
        final var achievements  = achievementsRepository.listAchievements().stream().map(achievement -> {
                    final var map = new HashMap<String, Object>();

                    map.put("identifier", achievement.identifier());
                    map.put("unlocked", false);

                    return map;
                })
                .toList();

        final var metadata      = new HashMap<String, Object>();
        metadata.put("achievements", achievements);

        authFacade.updateMetadata(String.format("auth0|%s", user.getUserId()), metadata);

        return user;
    }

    @Override
    public String googleAuth() {
        return authFacade.googleAuth();
    }

    @Override
    public TokenHolder handleGoogleResponse(String code) throws Auth0Exception {
        return authFacade.handleGoogleResponse(code);
    }

    @Override
    public String decode(String jwt) {
        return authFacade.decode(jwt);
    }
}
