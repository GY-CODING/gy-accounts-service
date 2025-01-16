package org.gycoding.accounts.infrastructure.api.dto.in.user;

import lombok.Builder;

@Builder
public record ProfileRQDTO(
    String email,
    String username,
    String phoneNumber,
    String picture
) { }