package org.gycoding.accounts.infrastructure.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UsernameRQDTO(
    @JsonProperty("username")
    String username
) { }