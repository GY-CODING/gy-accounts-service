package org.gycoding.accounts.application.service.products.gyclient;

import org.gycoding.accounts.application.dto.out.user.metadata.gyclient.GYClientMetadataODTO;
import org.gycoding.exceptions.model.APIException;

public interface ClientService {
    GYClientMetadataODTO getClientMetadata(String userID) throws APIException;
}