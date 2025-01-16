package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import lombok.*;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gyclient.GYClientMetadataRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.gymessages.GYMessagesMetadataRSDTO;
import org.gycoding.accounts.shared.GYCODINGRoles;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataRSDTO {
    private List<GYCODINGRoles> roles;
    private GYClientMetadataRSDTO gyClient;
    private GYMessagesMetadataRSDTO gyMessages;

    public Map<String, Object> toMap() {
        return Map.of(
                "roles", this.roles,
                "gyClient", this.gyClient,
                "gyMessages", this.gyMessages
        );
    }
}