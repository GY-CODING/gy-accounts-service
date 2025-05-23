package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import lombok.*;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gymessages.GYMessagesMetadataRSDTO;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataRSDTO {
    private GYMessagesMetadataRSDTO gyMessages;
    private ProfileRSDTO profile;
}