package org.gycoding.accounts.application.service.gyclient;

import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.exceptions.model.APIException;


public interface ClientRepository {
    GYClientMetadata getClientMetadata(String userID) throws APIException;
}