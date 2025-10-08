package org.gycoding.accounts.infrastructure.external.feign.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ManagementTokenFeignRSDTO(
        @JsonProperty("access_token")
        String accessToken
) { }
