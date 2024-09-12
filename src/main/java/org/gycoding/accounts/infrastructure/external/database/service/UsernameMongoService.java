package org.gycoding.accounts.infrastructure.external.database.service;

import org.gycoding.accounts.domain.entities.username.EntityUsername;
import org.gycoding.accounts.infrastructure.external.database.repository.UsernameMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsernameMongoService {
    @Autowired
    private UsernameMongoRepository usernameMongoRepository;

    public EntityUsername save(EntityUsername username) {
        return usernameMongoRepository.save(username);
    }

    public void delete(String username) {
        usernameMongoRepository.delete(this.getUsername(username));
    }

    public EntityUsername getUsername(String username) {
        return usernameMongoRepository.findByUsername(username);
    }

    public List<EntityUsername> listUsernames() {
        return usernameMongoRepository.findAll();
    }

    public boolean existsByUsername(String username) {
        boolean exists = false;

        for(EntityUsername entityUsername : this.listUsernames()) {
            if(entityUsername.username().equals(username)) {
                exists = true;
                break;
            }
        }

        return exists;
    }
}
