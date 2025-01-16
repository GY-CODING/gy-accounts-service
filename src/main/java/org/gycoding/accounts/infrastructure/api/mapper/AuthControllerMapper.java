package org.gycoding.accounts.infrastructure.api.mapper;

import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.auth.UserRQDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthControllerMapper {
    UserIDTO toIDTO(UserRQDTO profile);
}
