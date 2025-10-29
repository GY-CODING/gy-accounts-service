package org.gycoding.accounts.domain.repository;

import org.gycoding.quasar.exceptions.model.FacadeException;
import org.springframework.stereotype.Repository;

@Repository
public interface Auth0FeignFacade {
    String getManagementToken(String managementClientId, String managementClientSecret, String managementAudience, String managementGrantType) throws FacadeException;
}
