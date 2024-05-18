package org.gycoding.accounts.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserRQDTO(
    @JsonProperty("email")
    String email,
    @JsonProperty("user")
    String user,
    @JsonProperty("password")
    String password
) {}