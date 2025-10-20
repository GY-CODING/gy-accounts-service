package org.gycoding.accounts.infrastructure.external.feign.books;

import org.gycoding.accounts.infrastructure.external.feign.dto.in.ManagementTokenFeignRQDTO;
import org.gycoding.accounts.infrastructure.external.feign.dto.out.ManagementTokenFeignRSDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "GYBooks", url = "${gy.books.url}")
public interface BooksFeignClient {
    @PostMapping("/metadata")
    void setMetadata(@RequestHeader("x-profile-id") String profileId, @RequestHeader("x-api-key") String apiKey);
}
