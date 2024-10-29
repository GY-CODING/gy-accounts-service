package org.gycoding.accounts.infrastructure.api;

import org.gycoding.accounts.application.service.gyclient.ClientRepository;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientRepository clientService = null;

    @Autowired
    private AuthFacade authFacade = null;

    @GetMapping("/metadata/get")
    public ResponseEntity<?> getClientMetadata(
            @RequestHeader String token
    ) throws APIException {
        return ResponseEntity.ok(clientService.getClientMetadata(authFacade.decode(token)));
    }
}