package org.gycoding.accounts.infrastructure.api;

import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.exceptions.model.APIException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class HomeController {
    @GetMapping("/")
public String home() throws APIException {
    Resource resource = new ClassPathResource("static/index.html");

    try {
        return new String(Files.readAllBytes(Paths.get(resource.getURI())));
    } catch (IOException e) {
        throw new APIException(
                AccountsAPIError.HOME_NOT_FOUND.getCode(),
                AccountsAPIError.HOME_NOT_FOUND.getMessage(),
                AccountsAPIError.HOME_NOT_FOUND.getStatus()
        );
    }
}

    @GetMapping("/makemeacoffee")
    public String makeCoffee() throws APIException {
        throw new APIException(
                AccountsAPIError.I_AM_A_TEAPOT.getCode(),
                AccountsAPIError.I_AM_A_TEAPOT.getMessage(),
                AccountsAPIError.I_AM_A_TEAPOT.getStatus()
        );
    }
}
