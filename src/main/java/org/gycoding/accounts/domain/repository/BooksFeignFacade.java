package org.gycoding.accounts.domain.repository;

import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksFeignFacade {
    void setMetadata(String userId) throws APIException;
}
