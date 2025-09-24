package org.gycoding.accounts.infrastructure.external.feign;

import feign.FeignException;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.repository.Auth0FeignFacade;
import org.gycoding.accounts.infrastructure.external.feign.dto.in.ManagementTokenFeignRQDTO;
import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@AllArgsConstructor
public class Auth0FeignFacadeImpl implements Auth0FeignFacade {
    private final Auth0FeignClient feignClient;

    @Override
    public String getManagementToken(String managementClientId, String managementClientSecret, String managementAudience, String managementGrantType) throws APIException {
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
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        } catch (FeignException e) {
            throw new APIException(
                    AccountsAPIError.SERVER_ERROR.getCode(),
                    AccountsAPIError.SERVER_ERROR.getMessage(),
                    AccountsAPIError.SERVER_ERROR.getStatus()
            );
        }
    }
}
