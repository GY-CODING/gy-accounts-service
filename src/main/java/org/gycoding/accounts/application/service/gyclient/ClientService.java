package org.gycoding.accounts.application.service.gyclient;

import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements ClientRepository {
    @Autowired
    private AuthFacade authFacade = null;

    @Override
    public GYClientMetadata getClientMetadata(String userID) throws APIException {
        try {
            return authFacade.getClientMetadata(userID);
        } catch(Exception e) {
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }
}
