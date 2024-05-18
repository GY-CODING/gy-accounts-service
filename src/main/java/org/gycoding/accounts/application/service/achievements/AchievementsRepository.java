package org.gycoding.accounts.application.service.achievements;

import com.auth0.exception.Auth0Exception;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementsRepository {
    public void unlockAchievement(String userId, String achievementIdentifier) throws Auth0Exception;
}
