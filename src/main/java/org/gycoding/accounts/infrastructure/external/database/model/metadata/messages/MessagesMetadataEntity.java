package org.gycoding.accounts.infrastructure.external.database.model.metadata.messages;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MessagesMetadataEntity {
    private List<ChatEntity> chats;
}