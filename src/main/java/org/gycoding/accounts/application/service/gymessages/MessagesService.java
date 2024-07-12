package org.gycoding.accounts.application.service.gymessages;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.application.service.auth.AuthRepository;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
import org.gycoding.accounts.domain.entities.metadata.gymessages.ChatMetadata;
import org.gycoding.accounts.domain.entities.metadata.gymessages.GYMessagesMetadata;
import org.gycoding.accounts.domain.enums.ServerStatus;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.dto.ChatRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessagesService implements MessagesRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Override
    public void addChat(String userId, ChatRQDTO chatRQDTO) throws AccountsAPIException {
        try {
            var metadata = authFacade.getMetadata(userId);
            var gyMessagesMetadata = (Map<String, Object>) metadata.get("gyMessages");
            var chats = (List<Object>) gyMessagesMetadata.get("chats");

            for(Object chat : chats) {
                var castedChat = (Map<String, Object>) chat;

                if(castedChat.get("chatId").equals(chatRQDTO.chatId())) {
                    throw new AccountsAPIException(ServerStatus.CHAT_ALREADY_EXISTS);
                }
            }

            var chat = ChatMetadata.builder()
                    .chatId(chatRQDTO.chatId())
                    .isAdmin(chatRQDTO.isAdmin())
                    .build();

            chats.add(chat);

            authFacade.setMetadata(userId, metadata, Boolean.TRUE);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.INVALID_LOGIN);
        }
    }

    @Override
    public void removeChat(String userId, String chatId) throws AccountsAPIException {
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
            throw new AccountsAPIException(ServerStatus.INVALID_LOGIN);
        }
    }

    @Override
    public void setAdmin(String userId, ChatRQDTO chatRQDTO) throws AccountsAPIException {
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
                throw new AccountsAPIException(ServerStatus.CHAT_NOT_FOUND);
            }

            authFacade.setMetadata(userId, metadata, Boolean.TRUE);
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.INVALID_LOGIN);
        }
    }

    @Override
    public List<Object> listChats(String userId) throws AccountsAPIException {

        try {
            var metadata = authFacade.getMetadata(userId);
            var gyMessagesMetadata = (Map<String, Object>) metadata.get("gyMessages");
            return (List<Object>) gyMessagesMetadata.get("chats");
        } catch(Auth0Exception e) {
            throw new AccountsAPIException(ServerStatus.INVALID_LOGIN);
        }
    }
}
