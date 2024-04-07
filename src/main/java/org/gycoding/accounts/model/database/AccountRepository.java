package org.gycoding.accounts.model.database;

import org.gycoding.accounts.model.entities.Email;
import org.gycoding.accounts.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmail(Email email);
}
