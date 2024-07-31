package org.gycoding.accounts.application.service.gymessages;

import org.gycoding.accounts.infrastructure.dto.ChatRQDTO;
import org.gycoding.springexceptions.model.APIException;

import java.util.List;

public interface MessagesRepository {
    void addChat(String userId, ChatRQDTO chatRQDTO) throws APIException;
    void removeChat(String userId, String chatId) throws APIException;
    void setAdmin(String userId, ChatRQDTO chatRQDTO) throws APIException;
    List<Object> listChats(String userId) throws APIException;
}
