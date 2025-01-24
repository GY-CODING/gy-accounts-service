package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import lombok.*;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gymessages.GYMessagesMetadataRSDTO;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataRSDTO {
    private GYMessagesMetadataRSDTO gyMessages;
    private ProfileRSDTO profile;
}