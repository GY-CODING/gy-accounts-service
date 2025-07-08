package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.UUID;

@Builder
public record PublicProfileRSDTO(
    UUID id,
    String username,
    String phoneNumber,
    String picture
) { }