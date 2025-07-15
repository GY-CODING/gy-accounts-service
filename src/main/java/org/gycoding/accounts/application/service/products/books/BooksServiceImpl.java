package org.gycoding.accounts.application.service.products.books;

import com.auth0.exception.Auth0Exception;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.out.books.BooksProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.application.mapper.UserServiceMapper;
import org.gycoding.accounts.application.mapper.products.BooksServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestCommand;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.domain.repository.FriendRequestRepository;
import org.gycoding.accounts.domain.repository.MetadataRepository;
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

    private final FriendRequestRepository friendRequestRepository;

    private MetadataRepository metadataRepository;

    private final BooksServiceMapper mapper;

    @Override
    public BooksProfileODTO getProfile(UUID profileId) throws APIException {
        final var userMetadata = metadataRepository.get(profileId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return mapper.toODTO(userMetadata.profile(), userMetadata.books().biography());
    }

    @Override
    public BooksProfileODTO getProfile(String userId, UUID profileId) throws APIException {
        final var requestedUserMetadata = metadataRepository.get(profileId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        try {
            return mapper.toODTO(requestedUserMetadata.profile(), requestedUserMetadata.books().biography(), userMetadata.books().friends().contains(requestedUserMetadata.profile().id()));
        } catch(NullPointerException e) {
            return mapper.toODTO(requestedUserMetadata.profile(), requestedUserMetadata.books().biography());
        }
    }

    @Override
    public List<BooksProfileODTO> listFriends(String userId) throws APIException {
            final var userMetadata = metadataRepository.get(userId)
                    .orElseThrow(() -> new APIException(
                            AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                            AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                            AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                    ));

            final var friends = userMetadata.books().friends();

            if(friends.isEmpty()) {
                return List.of();
            }

        try {
            return friends.stream()
                    .map(friend -> metadataRepository.get(friend).orElseThrow(RuntimeException::new))
                    .map(MetadataMO::profile)
                    .map(profile -> mapper.toODTO(profile, userMetadata.books().biography()))
                    .toList();
        } catch (RuntimeException e) {
            throw new APIException(
                AccountsAPIError.SERVER_ERROR.getCode(),
                AccountsAPIError.SERVER_ERROR.getMessage(),
                AccountsAPIError.SERVER_ERROR.getStatus()
            );
        }
    }

    @Override
    public List<FriendRequestODTO> listFriendRequests(String userId) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        return friendRequestRepository.list(userMetadata.profile().id()).stream()
                .map(mapper::toODTO)
                .toList();
    }

    @Override
    public FriendRequestODTO sendFriendRequest(String userId, UUID to) throws APIException {
        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        if(userMetadata.books().friends().contains(to)) {
            Logger.error("User is already a friend.", to);

            throw new APIException(
                    AccountsAPIError.CONFLICT.getCode(),
                    AccountsAPIError.CONFLICT.getMessage(),
                    AccountsAPIError.CONFLICT.getStatus()
            );
        }

        try {
            return mapper.toODTO(friendRequestRepository.save(mapper.toMO(userMetadata.profile().id(), to)));
        } catch (Exception e) {
            throw new APIException(
                AccountsAPIError.CONFLICT.getCode(),
                AccountsAPIError.CONFLICT.getMessage(),
                AccountsAPIError.CONFLICT.getStatus()
            );
        }
    }

    @Override
    public void manageFriendRequest(String userId, String requestId, FriendRequestCommand command) throws APIException {
        final var persistedFriendRequest = friendRequestRepository.get(requestId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        final var userMetadata = metadataRepository.get(userId)
                .orElseThrow(() -> new APIException(
                        AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                        AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                ));

        if(!persistedFriendRequest.to().equals(userMetadata.profile().id())) throw new APIException(
                AccountsAPIError.FORBIDDEN.getCode(),
                AccountsAPIError.FORBIDDEN.getMessage(),
                AccountsAPIError.FORBIDDEN.getStatus()
        );

        if(command.equals(FriendRequestCommand.ACCEPT)) {
            final var senderUserMetadata = metadataRepository.get(persistedFriendRequest.from())
                    .orElseThrow(() -> new APIException(
                            AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                            AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                            AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
                    ));

            metadataRepository.update(
                    MetadataMO.builder()
                            .userId(senderUserMetadata.userId())
                            .books(
                                    BooksMetadataMO.builder()
                                            .friends(new ArrayList<>(senderUserMetadata.books().friends()) {{ add(userMetadata.profile().id()); }})
                                            .build()
                            )
                            .build()
            );

            metadataRepository.update(
                    MetadataMO.builder()
                            .userId(userId)
                            .books(
                                    BooksMetadataMO.builder()
                                            .friends(new ArrayList<>(userMetadata.books().friends()) {{ add(senderUserMetadata.profile().id()); }})
                                            .build()
                            )
                            .build()
            );
        }

        friendRequestRepository.delete(requestId);
    }

    @Override
    public String updateBiography(String userId, String biography) throws APIException {
        return metadataRepository.update(
                MetadataMO.builder()
                        .userId(userId)
                        .books(
                                BooksMetadataMO.builder()
                                        .biography(biography)
                                        .build()
                        )
                        .build()
        ).books().biography();
    }
}
