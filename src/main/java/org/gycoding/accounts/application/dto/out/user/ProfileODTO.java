package org.gycoding.accounts.application.dto.out.user;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Builder
public record ProfileODTO(
    String email,
    String username,
    String phoneNumber,
    List<AccountRoles> roles,
    String picture
) { }