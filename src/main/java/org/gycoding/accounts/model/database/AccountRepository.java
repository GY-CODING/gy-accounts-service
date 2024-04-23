package org.gycoding.accounts.model.database;

import org.gycoding.accounts.model.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
}
