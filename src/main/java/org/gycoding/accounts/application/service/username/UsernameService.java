package org.gycoding.accounts.application.service.username;

import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.accounts.domain.entities.username.EntityUsername;
import org.gycoding.accounts.infrastructure.external.database.service.UsernameMongoService;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsernameService implements UsernameRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private UsernameMongoService usernameMongoService = null;

    @Override
    public EntityUsername save(String userID, String username) throws APIException {
        if(usernameMongoService.existsByUsername(username)) {
            throw new APIException(
                    AccountsAPIError.USERNAME_ALREADY_EXISTS.getCode(),
                    AccountsAPIError.USERNAME_ALREADY_EXISTS.getMessage(),
                    AccountsAPIError.USERNAME_ALREADY_EXISTS.getStatus()
            );
        }

        final var entityUsername = EntityUsername.builder()
                .userId(userID)
                .username(username)
                .build();

        return usernameMongoService.save(entityUsername);
    }

    @Override
    public void delete(String username) throws APIException {
        usernameMongoService.delete(username);
    }

    @Override
    public EntityUsername getUsername(String username) throws APIException {
        return usernameMongoService.getUsername(username);
    }

    @Override
    public List<EntityUsername> listUsernames() throws APIException {
        return usernameMongoService.listUsernames();
    }
}
