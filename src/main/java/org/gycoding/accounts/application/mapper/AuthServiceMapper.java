package org.gycoding.accounts.application.mapper;

import org.gycoding.accounts.application.dto.in.auth.UserIDTO;
import org.gycoding.accounts.domain.model.auth.UserMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthServiceMapper {
    UserMO toMO(UserIDTO user);
}
