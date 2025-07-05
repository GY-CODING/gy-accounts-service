package org.gycoding.accounts.infrastructure.external.database.repository;

import org.gycoding.accounts.infrastructure.external.database.model.FriendRequestEntity;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestMongoRepository extends MongoRepository<FriendRequestEntity, String> {
    @Query("{ 'id' : ?0 }")
    Optional<FriendRequestEntity> findById(String id);

    void removeById(String id);
}
