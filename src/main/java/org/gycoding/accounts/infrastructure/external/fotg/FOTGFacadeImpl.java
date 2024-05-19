package org.gycoding.accounts.infrastructure.external.fotg;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.gycoding.accounts.domain.entities.achievements.Achievement;
import org.gycoding.accounts.domain.entities.achievements.AchievementTier;
import org.gycoding.accounts.infrastructure.external.unirest.UnirestFacade;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FOTGFacadeImpl implements FOTGFacade {
    @Value("${fotg.url}")
    private String FOTG_URL;

    @Override
    public Achievement find(Integer identifier) {
        final var url                   = String.format("%s/achievements/get?id=%d", this.FOTG_URL, identifier);

        final var response              = UnirestFacade.get(url);
        JSONObject jsonResponse         = new JSONObject(response.getBody());

        return Achievement.builder()
                .identifier(jsonResponse.getInt("identifier"))
                .name(jsonResponse.getString("name"))
                .description(jsonResponse.getString("description"))
                .image(jsonResponse.getString("image"))
                .tier(AchievementTier.valueOf(jsonResponse.getString("tier")))
                .build();
    }

    @Override
    public List<Achievement> findAll() {
        final var url                   = String.format("%s/achievements/list", this.FOTG_URL);

        final var response              = UnirestFacade.get(url);
        System.out.println(response.getBody());
        final var jsonResponse          = new JSONArray(response.getBody());

        List<Achievement> achievements  = new ArrayList<Achievement>();

        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject jsonAchievement = jsonResponse.getJSONObject(i);

            Achievement achievement = Achievement.builder()
                    .identifier(jsonAchievement.getInt("identifier"))
                    .name(jsonAchievement.getString("name"))
                    .description(jsonAchievement.getString("description"))
                    .image(jsonAchievement.getString("image"))
                    .tier(AchievementTier.valueOf(jsonAchievement.getString("tier")))
                    .build();

            achievements.add(achievement);
        }

        return achievements;
    }
}
