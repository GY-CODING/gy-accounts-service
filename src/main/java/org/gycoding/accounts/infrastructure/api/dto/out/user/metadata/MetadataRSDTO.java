package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import lombok.*;

@Builder
public record MetadataRSDTO(
    ProfileRSDTO profile
) { }