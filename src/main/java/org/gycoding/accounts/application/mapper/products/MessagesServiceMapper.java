package org.gycoding.accounts.application.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.gymessages.ChatODTO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.ChatMO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages.ChatRQDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessagesServiceMapper {
    ChatODTO toODTO(ChatMO profile);
    ChatMO toMO(ChatIDTO profile);
}
