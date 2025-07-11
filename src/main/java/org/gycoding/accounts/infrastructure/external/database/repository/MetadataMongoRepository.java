package org.gycoding.accounts.infrastructure.external.database.repository;

import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.gycoding.accounts.infrastructure.external.database.model.metadata.MetadataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MetadataMongoRepository extends MongoRepository<MetadataEntity, String> {
    Optional<MetadataEntity> findByUserId(String userId);
    Optional<MetadataEntity> findByProfileId(String profileId);
    Optional<MetadataEntity> findByProfileApiKey(String apiKey);

    @Query("{ 'profile.username' : { $regex: ?0, $options: 'i' } }")
    List<MetadataEntity> findAllByProfileUsername(String query);
}
