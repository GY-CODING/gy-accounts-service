package org.gycoding.accounts.shared.utils.logger;

import org.gycoding.accounts.infrastructure.external.unirest.UnirestFacade;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public interface Logger {
    @Value("${gy.logs.url}")
    String url = "";

    @Value("${gy.logs.token}")
    String token = "";

    static void log(LogDTO log) {
        final var headers = Map.of(
                "Authorization", token
        );

        UnirestFacade.post(url, headers, log.toString());
    }
}
