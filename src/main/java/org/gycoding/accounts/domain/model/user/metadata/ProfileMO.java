package org.gycoding.accounts.domain.model.user.metadata;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.UUID;

@Builder
public record ProfileMO(
    UUID id,
    String username,
    String email,
    String phoneNumber,
    List<AccountRoles> roles,
    String apiKey,
    String picture,
    Boolean isFriend
) { }