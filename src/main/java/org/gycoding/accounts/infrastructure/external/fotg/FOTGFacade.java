package org.gycoding.accounts.infrastructure.external.fotg;

import org.gycoding.accounts.domain.entities.achievements.Achievement;

public interface FOTGFacade {
    Achievement getAchievement(String identifier);
}
