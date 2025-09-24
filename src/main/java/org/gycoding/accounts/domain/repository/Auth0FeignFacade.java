package org.gycoding.accounts.domain.repository;

import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Repository;

@Repository
public interface Auth0FeignFacade {
    String getManagementToken(String managementClientId, String managementClientSecret, String managementAudience, String managementGrantType) throws APIException;
}
