package org.gycoding.accounts.application.dto.in.user.metadata;

import lombok.*;
import org.gycoding.accounts.application.dto.in.user.metadata.gyclient.GYClientMetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.GYMessagesMetadataIDTO;
import org.gycoding.accounts.shared.GYCODINGRoles;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataIDTO {
    public List<GYCODINGRoles> roles;
    public GYClientMetadataIDTO gyClient;
    public GYMessagesMetadataIDTO gyMessages;

    public Map<String, Object> toMap() {
        return Map.of(
                "roles", this.roles,
                "gyClient", this.gyClient,
                "gyMessages", this.gyMessages
        );
    }
}