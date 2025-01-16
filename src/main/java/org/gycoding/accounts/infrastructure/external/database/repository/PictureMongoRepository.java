package org.gycoding.accounts.infrastructure.external.database.repository;

import org.gycoding.accounts.infrastructure.external.database.model.PictureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureMongoRepository extends MongoRepository<PictureEntity, String> {
    PictureEntity findByName(String name);
}
