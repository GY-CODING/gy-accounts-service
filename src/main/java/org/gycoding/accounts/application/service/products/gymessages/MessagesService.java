package org.gycoding.accounts.application.service.products.gymessages;

import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatIDTO;
import org.gycoding.exceptions.model.APIException;

import java.util.List;

public interface MessagesService {
    void addChat(String userId, ChatIDTO chat) throws APIException;
    void removeChat(String userId, String chatId) throws APIException;
    void setAdmin(String userId, ChatIDTO chat) throws APIException;
    List<Object> listChats(String userId) throws APIException;
}
