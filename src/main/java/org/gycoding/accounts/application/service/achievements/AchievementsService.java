package org.gycoding.accounts.application.service.achievements;

import com.auth0.exception.Auth0Exception;
import org.gycoding.accounts.domain.entities.achievements.Achievement;
import org.gycoding.accounts.domain.enums.ServerStatus;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.dto.AchievementRSDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.accounts.infrastructure.external.fotg.FOTGFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AchievementsService implements AchievementsRepository {
    @Autowired
    private AuthFacade authFacade           = null;

    @Autowired
    private FOTGFacade fotgFacade           = null;

    @Override
    public List<Achievement> listAchievements() throws AccountsAPIException {
        try {
            return fotgFacade.findAll();
        } catch(Exception e) {
            throw new AccountsAPIException(ServerStatus.ACHIEVEMENTS_NOT_FOUND);
        }
    }

    @Override
    public List<AchievementRSDTO> listAchievements(String userId) throws AccountsAPIException {
        List<Achievement> achievements;
        List<HashMap<String, Object>> userAchievements;
        List<AchievementRSDTO> achievementsRSDTO = new ArrayList<>();

        try {
            achievements = fotgFacade.findAll();
        } catch(Exception e) {
            throw new AccountsAPIException(ServerStatus.ACHIEVEMENTS_NOT_FOUND);
        }

        try {
            userAchievements = (List<HashMap<String, Object>>) authFacade.getMetadata(userId).get("achievements");
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.COULD_NOT_GET_USER_METADATA);
        }

        for(Achievement achievement : achievements) {
            final var userAchievement = userAchievements.get(achievement.identifier() - 1);

            final var achievementParsed = AchievementRSDTO.builder()
                    .identifier((Integer) userAchievement.get("identifier"))
                    .name(achievement.name())
                    .description(achievement.description())
                    .image(achievement.image())
                    .tier(achievement.tier().toString())
                    .unlocked((Boolean) userAchievement.get("unlocked"))
                    .build();

            achievementsRSDTO.add(achievementParsed);
        }

        return achievementsRSDTO;
    }

    @Override
    public void unlockAchievement(String userId, Integer achievementID) throws AccountsAPIException {
        List<HashMap<String, Object>> achievements;
        try {
            achievements              = (List<HashMap<String, Object>>) authFacade.getMetadata(userId).get("achievements");
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.COULD_NOT_GET_USER_METADATA);
        }

        final var achievement               = achievements.get(achievementID - 1);
        achievement.put("unlocked", true);

        final var updatedAchievements      = new HashMap<String, Object>();
        updatedAchievements.put("achievements", achievements);

        try {
            authFacade.updateMetadata(userId, updatedAchievements);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.METADATA_NOT_UPDATED);
        }
    }
}
