package org.gycoding.accounts.application.service.auth;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import org.gycoding.accounts.domain.entities.database.gyclient.EntityUsername;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.exceptions.model.APIException;

public interface MetadataRepository {
    EntityUsername saveUsername(String userId, String username) throws APIException;
    void setupMetadata(String token) throws APIException;
}