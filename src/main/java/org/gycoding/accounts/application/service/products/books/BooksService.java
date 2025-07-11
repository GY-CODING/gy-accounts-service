package org.gycoding.accounts.application.service.products.books;

import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.dto.out.user.metadata.books.FriendRequestODTO;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestCommand;
import org.gycoding.exceptions.model.APIException;

import java.util.List;
import java.util.UUID;

public interface BooksService {
    List<ProfileODTO> listFriends(String userId) throws APIException;
    FriendRequestODTO sendFriendRequest(String userId, UUID to) throws APIException;
    List<FriendRequestODTO> listFriendRequests(String userId) throws APIException;
    void manageFriendRequest(String userId, String requestId, FriendRequestCommand command) throws APIException;
    String updateBiography(String userId, String biography) throws APIException;
}
