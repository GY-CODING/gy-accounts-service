package org.gycoding.accounts.domain.entities.achievements;

import lombok.Getter;

public enum AchievementTier {
    BRONZE("BRONZE"),
    SILVER("SILVER"),
    GOLD("GOLD"),
    PLATINUM("PLATINUM");

    @Getter
    public final String tier;

    private AchievementTier(String tier) {
        this.tier    = tier;
    }
}