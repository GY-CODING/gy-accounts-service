package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TransformIDRQDTO(
        String userId,
        String profileId
) { }
