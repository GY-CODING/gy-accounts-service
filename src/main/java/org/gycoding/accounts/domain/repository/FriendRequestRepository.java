package org.gycoding.accounts.domain.repository;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestMO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FriendRequestRepository {
    FriendRequestMO save(FriendRequestMO friendRequest);
    Optional<FriendRequestMO> get(String requestId);
    List<FriendRequestMO> list(UUID profileId);
    void delete(String requestId);
}
