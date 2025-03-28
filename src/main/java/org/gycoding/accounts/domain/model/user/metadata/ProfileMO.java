package org.gycoding.accounts.domain.model.user.metadata;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Builder
public record ProfileMO(
    String username,
    String phoneNumber,
    List<AccountRoles> roles,
    String apiKey,
    String picture
) { }