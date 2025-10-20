package org.gycoding.accounts.infrastructure.external.feign.books;

import feign.FeignException;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.repository.Auth0FeignFacade;
import org.gycoding.accounts.domain.repository.BooksFeignFacade;
import org.gycoding.accounts.infrastructure.external.feign.dto.in.ManagementTokenFeignRQDTO;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class BooksFeignFacadeImpl implements BooksFeignFacade {
    @Value("${allowed.apiKey}")
    private String apiKey;

    @Autowired
    private BooksFeignClient feignClient;

    @Override
    public void setMetadata(String userId) throws APIException {
        try {
            feignClient.setMetadata(userId, apiKey);
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
