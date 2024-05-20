package org.gycoding.accounts.application.service.achievements;

import com.auth0.exception.Auth0Exception;
import org.gycoding.accounts.domain.entities.achievements.Achievement;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.dto.AchievementRSDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementsRepository {
    List<Achievement> listAchievements() throws AccountsAPIException;
    List<AchievementRSDTO> listAchievements(String userId) throws AccountsAPIException;
    void unlockAchievement(String userId, Integer achievementID) throws AccountsAPIException;
}
