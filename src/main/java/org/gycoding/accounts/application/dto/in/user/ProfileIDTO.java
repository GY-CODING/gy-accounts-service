package org.gycoding.accounts.application.dto.in.user;

import lombok.Builder;

@Builder
public record ProfileIDTO(
    String email,
    String username,
    String phoneNumber,
    String picture
) { }