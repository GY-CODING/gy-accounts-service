package org.gycoding.accounts.application.service.gymessages;

import com.auth0.exception.Auth0Exception;
import org.gycoding.accounts.domain.entities.metadata.gymessages.ChatMetadata;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.dto.ChatRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessagesService implements MessagesRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Override
    public void addChat(String userId, ChatRQDTO chatRQDTO) throws APIException {
        try {
            var metadata = authFacade.getMetadata(userId);
            var gyMessagesMetadata = (Map<String, Object>) metadata.get("gyMessages");
            var chats = (List<Object>) gyMessagesMetadata.get("chats");

            for(Object chat : chats) {
                var castedChat = (Map<String, Object>) chat;

                if(castedChat.get("chatId").equals(chatRQDTO.chatId())) {
                    throw new APIException(
                            AccountsAPIError.CONFLICT.getCode(),
                            AccountsAPIError.CONFLICT.getMessage(),
                            AccountsAPIError.CONFLICT.getStatus()
                    );
                }
            }

            var chat = ChatMetadata.builder()
                    .chatId(chatRQDTO.chatId())
                    .isAdmin(chatRQDTO.isAdmin())
                    .build();

            chats.add(chat);

            authFacade.setMetadata(userId, metadata, Boolean.TRUE);
        } catch(Auth0Exception e) {
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
            throw new APIException(
                    AccountsAPIError.INVALID_LOGIN.getCode(),
                    AccountsAPIError.INVALID_LOGIN.getMessage(),
                    AccountsAPIError.INVALID_LOGIN.getStatus()
            );
        }
    }

    @Override
    public void setAdmin(String userId, ChatRQDTO chatRQDTO) throws APIException {
        boolean chatFound = false;

        try {
            var metadata = authFacade.getMetadata(userId);
            var gyMessagesMetadata = (Map<String, Object>) metadata.get("gyMessages");
            var chats = (List<Object>) gyMessagesMetadata.get("chats");

            for(Object chat : chats) {
                var castedChat = (Map<String, Object>) chat;

                if(castedChat.get("chatId").equals(chatRQDTO.chatId())) {
                    chatFound = true;
                    castedChat.replace("isAdmin", chatRQDTO.isAdmin());
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
            throw new APIException(
                    AccountsAPIError.INVALID_LOGIN.getCode(),
                    AccountsAPIError.INVALID_LOGIN.getMessage(),
                    AccountsAPIError.INVALID_LOGIN.getStatus()
            );
        }
    }
}
