package org.gycoding.accounts.infrastructure.external.feign.books;

import feign.FeignException;
import org.gycoding.accounts.domain.exceptions.AccountsError;
import org.gycoding.accounts.domain.repository.BooksFeignFacade;
import org.gycoding.quasar.exceptions.model.FacadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Service
public class BooksFeignFacadeImpl implements BooksFeignFacade {
    @Value("${allowed.apiKey}")
    private String apiKey;

    @Autowired
    private BooksFeignClient feignClient;

    @Override
    public void setMetadata(UUID profileId) throws FacadeException {
        try {
            feignClient.setMetadata(profileId.toString(), apiKey);
        } catch (FeignException.NotFound e) {
            throw new FacadeException(AccountsError.RESOURCE_NOT_FOUND);
        } catch (FeignException e) {
            throw new FacadeException(AccountsError.SERVER_ERROR);
        }
    }
}
