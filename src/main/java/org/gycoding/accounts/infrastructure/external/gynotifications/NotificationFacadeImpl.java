package org.gycoding.accounts.infrastructure.external.gynotifications;

import lombok.AllArgsConstructor;
import org.gycoding.accounts.domain.repository.NotificationsFacade;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationFacadeImpl implements NotificationsFacade {
    private SimpMessagingTemplate template;

    @Override
    public void notify(String message) {
        template.convertAndSend("/friends/request", message);
    }
}