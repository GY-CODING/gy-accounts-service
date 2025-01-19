package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gyclient.GYClientMetadataRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages.GYMessagesMetadataRQDTO;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class MetadataRQDTO {
    private List<AccountRoles> roles;
    private GYClientMetadataRQDTO gyClient;
    private GYMessagesMetadataRQDTO gyMessages;

    public Map<String, Object> toMap() {
        return Map.of(
                "roles", this.roles,
                "gyClient", this.gyClient,
                "gyMessages", this.gyMessages
        );
    }
}