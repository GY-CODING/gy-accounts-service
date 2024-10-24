package org.gycoding.accounts.application.service.gyclient;

import com.auth0.exception.Auth0Exception;
import org.gycoding.accounts.domain.entities.metadata.gyclient.FriendsMetadata;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.model.gyclient.Profile;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.accounts.domain.entities.database.gyclient.EntityUsername;
import org.gycoding.accounts.infrastructure.external.database.service.UsernameMongoService;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService implements ClientRepository {
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
    public EntityUsername getUsername(UUID userID) throws APIException {
        return usernameMongoService.getUsername(userID);
    }

    @Override
    public List<EntityUsername> listUsernames() throws APIException {
        return usernameMongoService.listUsernames();
    }

    @Override
    public GYClientMetadata getUserMetadata(String userID) throws APIException {
        try {
            return authFacade.getClientMetadata(userID);
        } catch(Exception e) {
            throw new APIException(
                    AccountsAPIError.METADATA_NOT_FOUND.getCode(),
                    AccountsAPIError.METADATA_NOT_FOUND.getMessage(),
                    AccountsAPIError.METADATA_NOT_FOUND.getStatus()
            );
        }
    }

    @Override
    public Profile getFriendProfile(String userID, String friendUserID) throws APIException {
        return null;
    }
}
