package org.gycoding.accounts.application.service.achievements;

import com.auth0.exception.Auth0Exception;
import org.gycoding.accounts.domain.entities.achievements.Achievement;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementsService implements AchievementsRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Override
    public void unlockAchievement(String userId, String achievementIdentifier) throws Auth0Exception {
        final var userMetadata          = authFacade.getMetadata(userId);
        final var achievements          = userMetadata.get("achievements");
        final var updatedAchievements   = Achievement.builder().build();

        authFacade.updateMetadata(userId, updatedAchievements.toMap());
    }
}
