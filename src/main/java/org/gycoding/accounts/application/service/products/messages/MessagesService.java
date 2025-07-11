package org.gycoding.accounts.application.service.products.messages;

import org.gycoding.accounts.application.dto.in.user.metadata.messages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.messages.ChatODTO;
import org.gycoding.exceptions.model.APIException;

import java.util.List;

public interface MessagesService {
    void addChat(String userId, ChatIDTO chat) throws APIException;
    void removeChat(String userId, String chatId) throws APIException;
    void setAdmin(String userId, ChatIDTO chat) throws APIException;
    List<ChatODTO> listChats(String userId) throws APIException;
}
