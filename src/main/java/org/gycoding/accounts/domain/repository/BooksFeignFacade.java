package org.gycoding.accounts.domain.repository;

import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BooksFeignFacade {
    void setMetadata(UUID profileId) throws APIException;
}
