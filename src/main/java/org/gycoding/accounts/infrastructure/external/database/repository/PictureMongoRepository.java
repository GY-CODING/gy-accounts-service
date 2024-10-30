package org.gycoding.accounts.infrastructure.external.database.repository;

import org.gycoding.accounts.domain.entities.database.EntityPicture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureMongoRepository extends MongoRepository<EntityPicture, String> {
    EntityPicture findByName(String name);
}
