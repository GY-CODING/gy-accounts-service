package org.gycoding.accounts.infrastructure.api;

import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.exceptions.model.APIException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/makemeacoffee")
    public String makeCoffee() throws APIException {
        throw new APIException(
                AccountsAPIError.I_AM_A_TEAPOT.getCode(),
                AccountsAPIError.I_AM_A_TEAPOT.getMessage(),
                AccountsAPIError.I_AM_A_TEAPOT.getStatus()
        );
    }
}
