package org.gycoding.accounts.domain.model.user.metadata;

import lombok.*;
import org.gycoding.accounts.domain.model.user.metadata.gyclient.GYClientMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.GYMessagesMetadataMO;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetadataMO {
    private List<AccountRoles> roles;
    private GYClientMetadataMO gyClient;
    private GYMessagesMetadataMO gyMessages;

    public Map<String, Object> toMap() {
        return Map.of(
                "roles", this.roles,
                "gyClient", this.gyClient,
                "gyMessages", this.gyMessages
        );
    }
}