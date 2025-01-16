package org.gycoding.accounts.domain.model.user;

import lombok.Builder;

@Builder
public record ProfileMO(
    String email,
    String username,
    String phoneNumber,
    String picture
) { }