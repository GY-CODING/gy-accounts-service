package org.gycoding.accounts.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record AchievementRSDTO(
    Integer identifier,
    String name,
    String description,
    String image,
    String tier,
    Boolean unlocked
) {}
