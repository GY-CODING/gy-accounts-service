package org.gycoding.accounts.domain.entities.achievements;

import lombok.Builder;

import java.util.Map;

@Builder
public record Achievement(
        String identifier,
        String name,
        String description,
        String image,
        AchievementTier tier
) {
    @Override
    public String toString() {
        return "{" +
                "\"identifier\": \"" + identifier + "\"," +
                "\"name\": \"" + name + "\"," +
                "\"description\": \"" + description + "\"," +
                "\"image\": \"" + image + "\"," +
                "\"tier\": \"" + tier + "\"" +
                "}";
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "identifier", identifier,
                "name", name,
                "description", description,
                "image", image,
                "tier", tier
        );
    }
}