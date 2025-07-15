package org.gycoding.accounts.domain.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsFacade {
    void notify(String message);
}
