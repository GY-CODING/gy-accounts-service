package org.gycoding.accounts.application.dto.out.user.metadata;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.UUID;

@Builder
public record ProfileODTO(
    UUID id,
    String username,
    String phoneNumber,
    List<AccountRoles> roles,
    String apiKey,
    String picture,
    Boolean isFriend
) { }