package org.gycoding.accounts.infrastructure.api.dto.out.user;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Builder
public record ProfileRSDTO(
    String email,
    String username,
    String phoneNumber,
    List<AccountRoles> roles,
    String picture
) { }