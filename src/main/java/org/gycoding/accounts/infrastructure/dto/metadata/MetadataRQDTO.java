package org.gycoding.accounts.infrastructure.dto.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;

@Builder
public record MetadataRQDTO(
    @JsonProperty("gyClient")
    GYClientMetadata gyClient,
    @JsonProperty("gyMessages")
    GYMessagesMetadata gyMessages
) { }