package org.gycoding.accounts.application.dto.in.user.metadata;

import lombok.Builder;

@Builder
public record ProfileIDTO(
    String username,
    String picture,
    String phoneNumber
) { }