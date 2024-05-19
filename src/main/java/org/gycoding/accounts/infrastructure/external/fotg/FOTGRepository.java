package org.gycoding.accounts.infrastructure.external.fotg;

import org.gycoding.accounts.domain.entities.achievements.Achievement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FOTGRepository extends MongoRepository<Achievement, Integer> {
    Optional<Achievement> findByIdentifier(Integer identifier);
}
