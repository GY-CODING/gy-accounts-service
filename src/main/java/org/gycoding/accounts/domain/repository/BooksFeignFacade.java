package org.gycoding.accounts.domain.repository;

import org.gycoding.quasar.exceptions.model.FacadeException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BooksFeignFacade {
    void setMetadata(UUID profileId) throws FacadeException;
}
