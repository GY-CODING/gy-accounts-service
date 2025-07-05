package org.gycoding.accounts.infrastructure.external.database.repository.impl;

import lombok.AllArgsConstructor;
import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.books.FriendRequestMO;
import org.gycoding.accounts.domain.repository.FriendRequestRepository;
import org.gycoding.accounts.domain.repository.PictureRepository;
import org.gycoding.accounts.infrastructure.external.database.mapper.FriendRequestDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.mapper.UserDatabaseMapper;
import org.gycoding.accounts.infrastructure.external.database.repository.FriendRequestMongoRepository;
import org.gycoding.accounts.infrastructure.external.database.repository.PictureMongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendRequestRepositoryImpl implements FriendRequestRepository {
    private final FriendRequestMongoRepository repository;

    private final FriendRequestDatabaseMapper mapper;

    @Override
    public FriendRequestMO save(FriendRequestMO friendRequest) {
        return mapper.toMO(repository.save(mapper.toEntity(friendRequest)));
    }

    @Override
    public Optional<FriendRequestMO> get(String requestId) {
        return repository.findById(requestId).map(mapper::toMO);
    }

    @Override
    public void delete(String requestId) {
        repository.removeById(requestId);
    }
}
