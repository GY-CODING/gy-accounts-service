package org.gycoding.accounts.application.service.products.gyclient;

import lombok.extern.slf4j.Slf4j;
import org.gycoding.accounts.application.dto.out.user.metadata.gyclient.GYClientMetadataODTO;
import org.gycoding.accounts.application.mapper.products.ClientServiceMapper;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private AuthFacade authFacade = null;

    @Qualifier("clientServiceMapperImpl")
    @Autowired
    private ClientServiceMapper mapper = null;

    @Override
    public GYClientMetadataODTO getClientMetadata(String userID) throws APIException {
        try {
            return mapper.toODTO(authFacade.getClientMetadata(userID));
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new APIException(
                    AccountsAPIError.RESOURCE_NOT_FOUND.getCode(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getMessage(),
                    AccountsAPIError.RESOURCE_NOT_FOUND.getStatus()
            );
        }
    }
}
