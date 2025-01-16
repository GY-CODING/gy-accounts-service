package org.gycoding.accounts.shared;

public enum AuthConnections {
    GOOGLE("google-oauth2"),
    BASIC("Username-Password-Authentication");

    public final String name;

    private AuthConnections(String name) {
        this.name = name;
    }
}
