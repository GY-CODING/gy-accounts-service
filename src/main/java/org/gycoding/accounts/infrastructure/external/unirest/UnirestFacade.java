package org.gycoding.accounts.infrastructure.external.unirest;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class UnirestFacade {
    public static HttpResponse<String> get(String url) {
        return Unirest.get(url)
                .header("content-type", "application/json")
                .asString();
    }

    public static HttpResponse<String> post(String url) {
        return Unirest.post(url)
                .header("content-type", "application/json")
                .asString();
    }

    public static HttpResponse<String> post(String url, String body) {
        return Unirest.post(url)
                .header("content-type", "application/json")
                .body(body)
                .asString();
    }
}
