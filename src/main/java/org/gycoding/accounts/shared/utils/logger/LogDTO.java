package org.gycoding.accounts.shared.utils.logger;

import lombok.Builder;

@Builder
public record LogDTO(
    LogLevel level,
    String message,
    Object data
) {}
