package org.gycoding.accounts.domain.model.user;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Builder
public record ProfileMO(
    String email,
    String username,
    String phoneNumber,
    List<AccountRoles> roles,
    String picture
) { }