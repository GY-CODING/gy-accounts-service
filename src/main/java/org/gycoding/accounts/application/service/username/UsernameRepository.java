package org.gycoding.accounts.application.service.username;

import org.gycoding.accounts.domain.entities.username.EntityUsername;
import org.gycoding.exceptions.model.APIException;

import java.util.List;

public interface UsernameRepository {
    EntityUsername save(String userID, String username) throws APIException;
    void delete(String username) throws APIException;
    EntityUsername getUsername(String username) throws APIException;
    List<EntityUsername> listUsernames() throws APIException;
}