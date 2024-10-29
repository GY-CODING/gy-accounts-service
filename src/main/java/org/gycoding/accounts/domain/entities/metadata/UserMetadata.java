package org.gycoding.accounts.domain.entities.metadata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;

import java.util.List;
import java.util.Map;

@Builder
@Setter
@Getter
public class UserMetadata {
    private String username;
    private String image;
    private List<GYCODINGRoles> roles;
    private GYClientMetadata gyClientMetadata;
    private GYMessagesMetadata gyMessagesMetadata;

    public Map<String, Object> toMap() {
        return Map.of(
                "username", username,
                "image", image,
                "roles", roles,
                "gyClientMetadata", gyClientMetadata,
                "gyMessagesMetadata", gyMessagesMetadata
        );
    }
}