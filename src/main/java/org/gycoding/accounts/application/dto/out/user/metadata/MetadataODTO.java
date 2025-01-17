package org.gycoding.accounts.application.dto.out.user.metadata;

import lombok.*;
import org.gycoding.accounts.application.dto.in.user.metadata.gyclient.GYClientMetadataIDTO;
import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.GYMessagesMetadataIDTO;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataODTO {
    private List<AccountRoles> roles;
    private GYClientMetadataIDTO gyClient;
    private GYMessagesMetadataIDTO gyMessages;

    public Map<String, Object> toMap() {
        return Map.of(
                "roles", this.roles,
                "gyClient", this.gyClient,
                "gyMessages", this.gyMessages
        );
    }
}