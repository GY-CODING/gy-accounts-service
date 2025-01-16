package org.gycoding.accounts.application.dto.out.user;

import lombok.Builder;
import org.gycoding.accounts.shared.GYCODINGRoles;

import java.util.List;

@Builder
public record ProfileODTO(
    String email,
    String username,
    String phoneNumber,
    List<GYCODINGRoles> roles,
    String picture
) { }