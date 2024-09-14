package org.gycoding.accounts.infrastructure.external.database.repository;

import org.gycoding.accounts.domain.entities.database.gyclient.EntityUsername;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsernameMongoRepository extends MongoRepository<EntityUsername, String> {
    EntityUsername findByUsername(String username);
    EntityUsername findByUserId(String userId);
}
