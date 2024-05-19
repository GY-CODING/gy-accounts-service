package org.gycoding.accounts.application.service.achievements;

import com.auth0.exception.Auth0Exception;
import org.gycoding.accounts.domain.entities.achievements.Achievement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementsRepository {
    List<Achievement> listAchievements();
    void unlockAchievement(String userId, Integer achievementID) throws Auth0Exception;
}
