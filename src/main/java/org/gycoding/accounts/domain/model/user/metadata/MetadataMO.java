package org.gycoding.accounts.domain.model.user.metadata;

import lombok.Builder;

@Builder
public record MetadataMO (
        String userId,
        ProfileMO profile
) { }