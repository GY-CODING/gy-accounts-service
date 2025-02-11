package org.gycoding.accounts.shared.utils.logger;

import org.gycoding.accounts.infrastructure.external.unirest.UnirestFacade;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class Logger {
    @Value("${gy.logs.url}")
    private static String url;

    @Value("${gy.logs.token}")
    private static String token;

    public static void info(String message, Object data) {
        final var headers = Map.of(
                "Authorization", token
        );

        UnirestFacade.post(url, headers, String.format("{\"level\": \"%s\", \"message\": \"%s\", \"data\": %s}", LogLevel.INFO, message, data));
    }

    public static void debug(String message, Object data) {
        final var headers = Map.of(
                "Authorization", token
        );

        UnirestFacade.post(url, headers, String.format("{\"level\": \"%s\", \"message\": \"%s\", \"data\": %s}", LogLevel.DEBUG, message, data));
    }

    public static void warn(String message, Object data) {
        final var headers = Map.of(
                "Authorization", token
        );

        UnirestFacade.post(url, headers, String.format("{\"level\": \"%s\", \"message\": \"%s\", \"data\": %s}", LogLevel.WARN, message, data));
    }

    public static void error(String message, Object data) {
        final var headers = Map.of(
                "Authorization", token
        );

        UnirestFacade.post(url, headers, String.format("{\"level\": \"%s\", \"message\": \"%s\", \"data\": %s}", LogLevel.ERROR, message, data));
    }
}
