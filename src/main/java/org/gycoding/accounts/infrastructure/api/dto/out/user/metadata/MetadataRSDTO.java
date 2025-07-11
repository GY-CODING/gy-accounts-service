package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import lombok.*;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.messages.MessagesMetadataRSDTO;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataRSDTO {
    private MessagesMetadataRSDTO messages;
    private ProfileRSDTO profile;
}