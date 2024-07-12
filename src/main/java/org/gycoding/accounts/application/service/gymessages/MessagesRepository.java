package org.gycoding.accounts.application.service.gymessages;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.dto.ChatRQDTO;

import java.util.List;

public interface MessagesRepository {
    void addChat(String userId, ChatRQDTO chatRQDTO) throws AccountsAPIException;
    void removeChat(String userId, String chatId) throws AccountsAPIException;
    void setAdmin(String userId, ChatRQDTO chatRQDTO) throws AccountsAPIException;
    List<Object> listChats(String userId) throws AccountsAPIException;
}
