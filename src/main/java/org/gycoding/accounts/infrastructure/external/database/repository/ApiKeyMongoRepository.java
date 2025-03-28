package org.gycoding.accounts.infrastructure.external.database.repository;

import org.gycoding.accounts.infrastructure.external.database.model.ApiKeyEntity;
import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyMongoRepository extends MongoRepository<ApiKeyEntity, String> {
    ApiKeyEntity findByApiKey(String apiKey);
    ApiKeyEntity findByUserId(String userId);
}
