package org.gycoding.accounts.infrastructure.api.controller.products;

import org.gycoding.accounts.application.service.products.gyclient.ClientService;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.infrastructure.api.mapper.products.ClientControllerMapper;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService service = null;

    @Qualifier("clientControllerMapperImpl")
    @Autowired
    private ClientControllerMapper mapper = null;

    @GetMapping("/metadata")
    public ResponseEntity<?> getClientMetadata(
            @RequestHeader("x-api-key") String userId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.getClientMetadata(userId)));
    }
}