package org.gycoding.accounts.infrastructure.api.dto.in.user.metadata;

import lombok.Builder;

@Builder
public record ProfileRQDTO(
    String username,
    String picture,
    String phoneNumber
) { }