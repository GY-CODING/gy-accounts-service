package org.gycoding.accounts.infrastructure.external.feign.auth0;

import org.gycoding.accounts.infrastructure.external.feign.dto.in.ManagementTokenFeignRQDTO;
import org.gycoding.accounts.infrastructure.external.feign.dto.out.ManagementTokenFeignRSDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth0", url = "${auth0.management.token.url}")
public interface Auth0FeignClient {
    @PostMapping("")
    ManagementTokenFeignRSDTO getManagementToken(@RequestBody ManagementTokenFeignRQDTO managementToken);
}
