package org.gycoding.accounts.model.database;

import org.gycoding.accounts.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<User, Integer> {

}
