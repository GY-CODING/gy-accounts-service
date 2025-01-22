package org.gycoding.accounts.application.dto.in.user;

import lombok.Builder;

@Builder
public record ProfileIDTO(
    String username,
    String picture
) { }