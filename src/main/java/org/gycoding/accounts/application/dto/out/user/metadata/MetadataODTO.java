package org.gycoding.accounts.application.dto.out.user.metadata;

import lombok.*;

@Builder
public record MetadataODTO(
        ProfileODTO profile
) { }