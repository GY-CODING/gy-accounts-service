package org.gycoding.accounts.infrastructure.external.feign.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ManagementTokenFeignRQDTO(
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("client_secret")
        String clientSecret,
        String audience,
        @JsonProperty("grant_type")
        String grantType
) { }
