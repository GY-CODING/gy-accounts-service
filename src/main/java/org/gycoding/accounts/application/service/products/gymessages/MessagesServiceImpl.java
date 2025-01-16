package org.gycoding.accounts.application.service.products.gymessages;

import com.auth0.exception.Auth0Exception;
import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatIDTO;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.ChatMetadataMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    private AuthFacade authFacade = null;

    @Override
    public void addChat(String userId, ChatIDTO chat) throws APIException {
        try {
            var metadata = authFacade.getMetadata(userId);
            var gyMessagesMetadata = (Map<String, Object>) metadata.get("gyMessages");
            var chats = (List<Object>) gyMessagesMetadata.get("chats");

            for(Object chatObject : chats) {
                var castedChat = (Map<String, Object>) chatObject;

                if(castedChat.get("chatId").equals(chat.chatId())) {
                    throw new APIException(
                            AccountsAPIError.CONFLICT.getCode(),
                            AccountsAPIError.CONFLICT.getMessage(),
                            AccountsAPIError.CONFLICT.getStatus()
                    );
                }
            }

            var chatMetadata = ChatMetadataMO.builder()
                    .chatId(chat.chatId())
                    .isAdmin(chat.isAdmin())
                    .build();

            chats.add(chatMetadata);

            authFacade.setMetadata(userId, metadata, Boolean.TRUE);
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
            var gyMessagesMetadata = (Map<String, Object>) metadata.get("gyMessages");
            var chats = (List<Object>) gyMessagesMetadata.get("chats");

            for(Object chat : chats) {
                var castedChat = (Map<String, Object>) chat;

                if(castedChat.get("chatId").equals(chatId)) {
                    chats.remove(chat);
                    break;
                }
            }

            authFacade.setMetadata(userId, metadata, Boolean.TRUE);
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
            var gyMessagesMetadata = (Map<String, Object>) metadata.get("gyMessages");
            var chats = (List<Object>) gyMessagesMetadata.get("chats");

            for(Object chatObject : chats) {
                var castedChat = (Map<String, Object>) chatObject;

                if(castedChat.get("chatId").equals(chat.chatId())) {
                    chatFound = true;
                    castedChat.replace("isAdmin", chat.isAdmin());
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

            authFacade.setMetadata(userId, metadata, Boolean.TRUE);
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
    public List<Object> listChats(String userId) throws APIException {
        try {
            var metadata = authFacade.getMetadata(userId);
            var gyMessagesMetadata = (Map<String, Object>) metadata.get("gyMessages");
            return (List<Object>) gyMessagesMetadata.get("chats");
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
