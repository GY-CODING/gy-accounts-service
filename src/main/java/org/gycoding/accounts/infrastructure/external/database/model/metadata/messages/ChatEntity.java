package org.gycoding.accounts.infrastructure.external.database.model.metadata.messages;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ChatEntity {
    private String chatId;
    private boolean isAdmin;
}