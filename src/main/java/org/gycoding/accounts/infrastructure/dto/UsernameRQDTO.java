package org.gycoding.accounts.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UsernameRQDTO(
    @JsonProperty("username")
    String username
) { }