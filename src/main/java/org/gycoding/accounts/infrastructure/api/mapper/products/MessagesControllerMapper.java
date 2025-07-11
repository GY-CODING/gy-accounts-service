package org.gycoding.accounts.infrastructure.api.mapper.products;

import org.gycoding.accounts.application.dto.in.user.metadata.messages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.messages.ChatODTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.messages.ChatRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.messages.ChatRSDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessagesControllerMapper {
    ChatIDTO toIDTO(ChatRQDTO profile);
    ChatRSDTO toRSDTO(ChatODTO profile);
}
