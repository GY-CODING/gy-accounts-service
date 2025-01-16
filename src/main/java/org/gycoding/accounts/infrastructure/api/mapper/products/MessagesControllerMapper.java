package org.gycoding.accounts.infrastructure.api.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatIDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages.ChatRQDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessagesControllerMapper {
    ChatIDTO toIDTO(ChatRQDTO profile);
}
