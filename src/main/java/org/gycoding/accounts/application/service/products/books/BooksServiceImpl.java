package org.gycoding.accounts.application.service.products.books;

import com.auth0.exception.Auth0Exception;
import kong.unirest.json.JSONObject;
import kotlin.Metadata;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.in.user.metadata.gymessages.ChatIDTO;
import org.gycoding.accounts.application.dto.out.user.metadata.MetadataODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.gymessages.ChatODTO;
import org.gycoding.accounts.application.mapper.UserServiceMapper;
import org.gycoding.accounts.application.mapper.products.BooksServiceMapper;
import org.gycoding.accounts.application.mapper.products.MessagesServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.metadata.ProfileMO;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestCommand;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.ChatMO;
import org.gycoding.accounts.domain.model.user.metadata.gymessages.GYMessagesMetadataMO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.domain.repository.FriendRequestRepository;
import org.gycoding.exceptions.model.APIException;
import org.gycoding.logs.logger.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final AuthFacade authFacade;

    private final FriendRequestRepository repository;

    private final BooksServiceMapper booksMapper;

    private final UserServiceMapper userMapper;

    @Override
    public List<ProfileODTO> listFriends(String userId) throws APIException {
        try {
            final var metadata = authFacade.getMetadata(userId);
            final var friends = metadata.getBooks().friends();

            if(friends.isEmpty()) {
                return List.of();
            }

            return friends.stream()
                    .map(friend -> {
                        try {
                            return authFacade.getMetadata(authFacade.findUserId(friend)).getProfile();
                        } catch (Auth0Exception e) {
                            throw new RuntimeException();
                        }
                    })
                    .map(userMapper::toODTO)
                    .toList();
        } catch (RuntimeException | Auth0Exception e) {
            throw new APIException(
                AccountsAPIError.AUTH_ERROR.getCode(),
                AccountsAPIError.AUTH_ERROR.getMessage(),
                AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }

    @Override
    public FriendRequestODTO sendFriendRequest(String userId, UUID to) throws APIException {
        try {
            if(authFacade.getMetadata(userId).getBooks().friends().contains(to)) {
                Logger.error("User is already a friend.", to);
                throw new APIException(
                    AccountsAPIError.CONFLICT.getCode(),
                    AccountsAPIError.CONFLICT.getMessage(),
                    AccountsAPIError.CONFLICT.getStatus()
                );
            }

            return booksMapper.toODTO(repository.save(booksMapper.toMO(userId, to)));
        } catch (Auth0Exception e) {
            throw new APIException(
                AccountsAPIError.AUTH_ERROR.getCode(),
                AccountsAPIError.AUTH_ERROR.getMessage(),
                AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }

    @Override
    public List<FriendRequestODTO> listFriendRequests(String userId) throws APIException {
        try {
            final var profileId = authFacade.getMetadata(userId).getProfile().id();

            return repository.list(profileId).stream()
                    .map(booksMapper::toODTO)
                    .toList();
        } catch (Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.AUTH_ERROR.getCode(),
                    AccountsAPIError.AUTH_ERROR.getMessage(),
                    AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }

    @Override
    public void manageFriendRequest(String userId, String requestId, FriendRequestCommand command) throws APIException {
        try {
            final var persistedFriendRequest = repository.get(requestId)
                    .orElseThrow(() -> new APIException(
                            AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                            AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                            AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                    ));

            final var userMetadata = authFacade.getMetadata(userId);

            if(!persistedFriendRequest.to().equals(userMetadata.getProfile().id())) throw new APIException(
                    AccountsAPIError.FORBIDDEN.getCode(),
                    AccountsAPIError.FORBIDDEN.getMessage(),
                    AccountsAPIError.FORBIDDEN.getStatus()
            );

            if(command.equals(FriendRequestCommand.ACCEPT)) {
                final var senderUserMetadata = authFacade.getMetadata(persistedFriendRequest.from());
                var senderUserFriends = senderUserMetadata.getBooks().friends();

                senderUserMetadata.setBooks(
                        BooksMetadataMO.builder()
                                .friends(new ArrayList<>(senderUserFriends) {{ add(userMetadata.getProfile().id()); }})
                                .build()
                );

                var userFriends = userMetadata.getBooks().friends();

                userMetadata.setBooks(
                        BooksMetadataMO.builder()
                                .friends(new ArrayList<>(userFriends) {{ add(senderUserMetadata.getProfile().id()); }})
                                .build()
                );

                authFacade.setMetadata(persistedFriendRequest.from(), senderUserMetadata);
                authFacade.setMetadata(userId, userMetadata);
            }

            repository.delete(requestId);
        } catch (Auth0Exception e) {
            throw new APIException(
                    AccountsAPIError.AUTH_ERROR.getCode(),
                    AccountsAPIError.AUTH_ERROR.getMessage(),
                    AccountsAPIError.AUTH_ERROR.getStatus()
            );
        }
    }
}
