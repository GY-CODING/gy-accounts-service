package org.gycoding.accounts.application.service.products.messages;

import kong.unirest.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.in.user.metadata.messages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.messages.ChatODTO;
import org.gycoding.accounts.application.mapper.products.MessagesServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.messages.ChatMO;
import org.gycoding.accounts.domain.model.user.metadata.messages.MessagesMetadataMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.domain.repository.MetadataRepository;
import org.gycoding.exceptions.model.APIException;
import org.gycoding.logs.logger.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessagesServiceImpl implements MessagesService {
    private final AuthFacade authFacade;

    private MetadataRepository metadataRepository;

    private final MessagesServiceMapper mapper;

    @Override
    public void addChat(String userId, ChatIDTO chat) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        var chats = userMetadata.messages().chats();

        for(ChatMO chatObject : chats) {
            if(chatObject.chatId().equals(chat.chatId())) {
                Logger.error("Chat already exists on user's metadata.", new JSONObject().put("chatId", chat.chatId()).put("userId", userId));

                throw new APIException(
                        AccountsAPIError.CONFLICT.getCode(),
                        AccountsAPIError.CONFLICT.getMessage(),
                        AccountsAPIError.CONFLICT.getStatus()
                );
            }
        }

        metadataRepository.update(
                MetadataMO.builder()
                        .userId(userId)
                        .messages(
                                MessagesMetadataMO.builder()
                                        .chats(new ArrayList<>(chats) {{ add(
                                                ChatMO.builder()
                                                        .chatId(chat.chatId())
                                                        .isAdmin(chat.isAdmin())
                                                        .build()
                                        ); }})
                                        .build()
                        )
                        .build()
        );
    }

    @Override
    public void removeChat(String userId, String chatId) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        var chats = userMetadata.messages().chats();

        for(ChatMO chat : chats) {
            if(chat.chatId().equals(chatId)) {
                metadataRepository.update(
                        MetadataMO.builder()
                                .userId(userId)
                                .messages(
                                        MessagesMetadataMO.builder()
                                                .chats(new ArrayList<>(chats) {{ remove(chat); }})
                                                .build()
                                )
                                .build()
                );
                break;
            }
        }
    }

    @Override
    public void setAdmin(String userId, ChatIDTO chat) throws APIException {
        boolean chatFound = false;

        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        var chats = userMetadata.messages().chats();

        for(ChatMO chatObject : chats) {
            if(chatObject.chatId().equals(chat.chatId())) {
                chatFound = true;

                metadataRepository.update(
                        MetadataMO.builder()
                                .userId(userId)
                                .messages(
                                        MessagesMetadataMO.builder()
                                                .chats(new ArrayList<>(chats) {{
                                                    remove(chat);
                                                    add(
                                                            ChatMO.builder()
                                                                    .chatId(chat.chatId())
                                                                    .isAdmin(chat.isAdmin())
                                                                    .build()
                                                    );
                                                }})
                                                .build()
                                )
                                .build()
                );

                break;
            }
        }

        if(!chatFound) {
            Logger.error("Chat was not found on user's metadata.", new JSONObject().put("chatId", chat.chatId()).put("userId", userId));

            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public List<ChatODTO> listChats(String userId) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return userMetadata.messages().chats().stream().map(mapper::toODTO).toList();
    }
}
