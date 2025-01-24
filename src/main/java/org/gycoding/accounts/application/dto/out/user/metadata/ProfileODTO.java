package org.gycoding.accounts.application.dto.out.user.metadata;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Builder
public record ProfileODTO(
    String username,
    String phoneNumber,
    List<AccountRoles> roles,
    String picture
) { }