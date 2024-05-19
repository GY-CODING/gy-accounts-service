package org.gycoding.accounts.infrastructure.external.fotg;

import org.gycoding.accounts.domain.entities.achievements.Achievement;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FOTGFacade {
    Achievement find(Integer identifier);
    List<Achievement> findAll();
}
