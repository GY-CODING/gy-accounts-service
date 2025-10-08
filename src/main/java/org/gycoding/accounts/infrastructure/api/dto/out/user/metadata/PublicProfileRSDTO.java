package org.gycoding.accounts.infrastructure.api.dto.out.user.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.gycoding.accounts.shared.AccountRoles;

import java.util.List;
import java.util.UUID;

@Builder
public record PublicProfileRSDTO(
    UUID id,
    String username,
    String email,
    String phoneNumber,
    String picture,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean isFriend
) { }