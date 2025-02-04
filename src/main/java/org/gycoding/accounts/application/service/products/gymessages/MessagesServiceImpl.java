package org.gycoding.accounts.application.service.products.gymessages;

import com.auth0.exception.Auth0Exception;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.gymessages.ChatODTO;
import org.gycoding.accounts.application.mapper.products.MessagesServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.ChatMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.GYMessagesMetadataMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessagesServiceImpl implements MessagesService {
    private final AuthFacade authFacade;

    private final MessagesServiceMapper mapper;

    @Override
    public void addChat(String userId, ChatIDTO chat) throws APIException {
        try {
            var metadata = authFacade.getMetadata(userId);
            var chats = metadata.getGyMessages().chats();

            for(ChatMO chatObject : chats) {
                if(chatObject.chatId().equals(chat.chatId())) {
                    throw new APIException(
                            AccountsAPIError.CONFLICT.getCode(),
                            AccountsAPIError.CONFLICT.getMessage(),
                            AccountsAPIError.CONFLICT.getStatus()
                    );
                }
            }

            var chatMetadata = ChatMO.builder()
                    .chatId(chat.chatId())
                    .isAdmin(chat.isAdmin())
                    .build();

            metadata.setGyMessages(
                    GYMessagesMetadataMO.builder()
                        .chats(
                                new ArrayList<>(chats) {{ add(chatMetadata); }}
                        )
                        .build()
            );

            authFacade.setMetadata(userId, metadata);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.INVALID_LOGIN.getCode(),
                    AccountsAPIError.INVALID_LOGIN.getMessage(),
                    AccountsAPIError.INVALID_LOGIN.getStatus()
            );
        }
    }

    @Override
    public void removeChat(String userId, String chatId) throws APIException {
        try {
            var metadata = authFacade.getMetadata(userId);
            var chats = metadata.getGyMessages().chats();

            for(ChatMO chat : chats) {
                if(chat.chatId().equals(chatId)) {
                    metadata.setGyMessages(
                            GYMessagesMetadataMO.builder()
                                    .chats(
                                            new ArrayList<>(chats) {{ remove(chat); }}
                                    )
                                    .build()
                    );
                    break;
                }
            }

            authFacade.setMetadata(userId, metadata);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.INVALID_LOGIN.getCode(),
                    AccountsAPIError.INVALID_LOGIN.getMessage(),
                    AccountsAPIError.INVALID_LOGIN.getStatus()
            );
        }
    }

    @Override
    public void setAdmin(String userId, ChatIDTO chat) throws APIException {
        boolean chatFound = false;

        try {
            var metadata = authFacade.getMetadata(userId);
            var chats = metadata.getGyMessages().chats();

            for(ChatMO chatObject : chats) {
                if(chatObject.chatId().equals(chat.chatId())) {
                    chatFound = true;
                    chatObject = ChatMO.builder()
                            .chatId(chat.chatId())
                            .isAdmin(chat.isAdmin())
                            .build();
                    break;
                }
            }

            if(!chatFound) {
                throw new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                );
            }

            authFacade.setMetadata(userId, metadata);
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.INVALID_LOGIN.getCode(),
                    AccountsAPIError.INVALID_LOGIN.getMessage(),
                    AccountsAPIError.INVALID_LOGIN.getStatus()
            );
        }
    }

    @Override
    public List<ChatODTO> listChats(String userId) throws APIException {
        try {
            var metadata = authFacade.getMetadata(userId);
            return metadata.getGyMessages().chats().stream().map(mapper::toODTO).toList();
        } catch(Auth0Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.INVALID_LOGIN.getCode(),
                    AccountsAPIError.INVALID_LOGIN.getMessage(),
                    AccountsAPIError.INVALID_LOGIN.getStatus()
            );
        }
    }
}
