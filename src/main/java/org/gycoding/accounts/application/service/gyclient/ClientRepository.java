package org.gycoding.accounts.application.service.gyclient;

import org.gycoding.accounts.domain.entities.database.gyclient.EntityUsername;
import org.gycoding.accounts.domain.entities.metadata.gyclient.GYClientMetadata;
import org.gycoding.accounts.domain.entities.model.gyclient.Profile;
import org.gycoding.exceptions.model.APIException;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {
    EntityUsername save(String userID, String username) throws APIException;
    void delete(String username) throws APIException;
    EntityUsername getUsername(String username) throws APIException;
    EntityUsername getUsername(UUID userID) throws APIException;
    List<EntityUsername> listUsernames() throws APIException;

    GYClientMetadata getUserMetadata(String userID) throws APIException;
    Profile getFriendProfile(String userID, String friendUserID) throws APIException;
}