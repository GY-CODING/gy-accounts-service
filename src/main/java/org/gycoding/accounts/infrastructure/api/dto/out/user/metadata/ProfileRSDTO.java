package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;

@Builder
public record ProfileRSDTO(
    String username,
    String phoneNumber,
    List<AccountRoles> roles,
    String apiKey,
    String picture
) { }