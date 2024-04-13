package org.gycoding.accountsv2.infrastructure.adapter.database.jpa;

import org.gycoding.accountsv2.domain.model.Email;
import org.gycoding.accountsv2.domain.model.UserMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<UserMO, Integer> {
    public Optional<UserMO> findByUsername(String username);
    public Optional<UserMO> findByEmail(Email email);
}
