package org.gycoding.accounts.infrastructure.dto.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;

import java.util.List;

@Builder
public record MetadataRQDTO(
    @JsonProperty("username")
    String username,
    @JsonProperty("image")
    String image,
    @JsonProperty("roles")
    List<GYCODINGRoles> roles,
    @JsonProperty("gyClient")
    GYClientMetadata gyClient,
    @JsonProperty("gyMessages")
    GYMessagesMetadata gyMessages
) { }