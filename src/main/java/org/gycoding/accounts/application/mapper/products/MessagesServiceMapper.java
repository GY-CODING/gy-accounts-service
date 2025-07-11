package org.gycoding.accounts.application.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.messages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.messages.ChatODTO;
import org.gycoding.accounts.domain.model.user.metadata.messages.ChatMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessagesServiceMapper {
    ChatODTO toODTO(ChatMO profile);
    ChatMO toMO(ChatIDTO profile);
}
