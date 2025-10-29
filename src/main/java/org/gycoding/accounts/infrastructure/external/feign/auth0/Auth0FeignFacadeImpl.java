package org.gycoding.accounts.infrastructure.external.feign.auth0;

import feign.FeignException;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.domain.exceptions.AccountsError;
import org.gycoding.accounts.domain.repository.Auth0FeignFacade;
import org.gycoding.accounts.infrastructure.external.feign.dto.in.ManagementTokenFeignRQDTO;
import org.gycoding.quasar.exceptions.model.FacadeException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@AllArgsConstructor
public class Auth0FeignFacadeImpl implements Auth0FeignFacade {
    private final Auth0FeignClient feignClient;

    @Override
    public String getManagementToken(String managementClientId, String managementClientSecret, String managementAudience, String managementGrantType) throws FacadeException {
        try {
            return feignClient.getManagementToken(
                    ManagementTokenFeignRQDTO.builder()
                            .clientId(managementClientId)
                            .clientSecret(managementClientSecret)
                            .audience(managementAudience)
                            .grantType(managementGrantType)
                            .build()
            ).accessToken();
        } catch (FeignException.NotFound e) {
            throw new FacadeException(AccountsError.RESOURCE_NOT_FOUND);
        } catch (FeignException e) {
            throw new FacadeException(AccountsError.SERVER_ERROR);
        }
    }
}
