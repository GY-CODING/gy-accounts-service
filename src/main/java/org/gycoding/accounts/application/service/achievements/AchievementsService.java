package org.gycoding.accounts.application.service.achievements;

import com.auth0.exception.Auth0Exception;
import org.gycoding.accounts.domain.entities.achievements.Achievement;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.accounts.infrastructure.external.fotg.FOTGRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AchievementsService implements AchievementsRepository {
    @Autowired
    private AuthFacade authFacade           = null;

    @Autowired
    private FOTGRepository fotgRepository   = null;

    @Override
    public List<Achievement> getAchievements() {
        return fotgRepository.findAll();
    }

    @Override
    public void unlockAchievement(String userId, Integer achievementID) throws Auth0Exception {
        final var achievements              = (List<HashMap<String, Object>>) authFacade.getMetadata(userId).get("achievements");

        final var achievement               = achievements.get(achievementID);
        achievement.put("unlocked", true);

        final var updatedAchievements      = new HashMap<String, Object>();
        updatedAchievements.put("achievements", achievements);

        authFacade.updateMetadata(userId, updatedAchievements);
    }
}
