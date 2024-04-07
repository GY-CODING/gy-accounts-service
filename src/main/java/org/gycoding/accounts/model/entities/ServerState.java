package org.gycoding.accounts.model.entities;

public enum ServerState {
    SUCCESS(0),
    INVALID_USERNAME(-1),
    INVALID_EMAIL(-2),
    INVALID_PASSWORD(-3),
    INVALID_LOGIN(-4),
    INVALID_SIGNUP(-5),
    INVALID_UPDATE(-6),
    DATABASE_ERROR(-7),
    INVALID_EMISOR(-8),
    INVALID_RECEPTOR(-9);

    public Integer state;

    ServerState(Integer state) {
        this.state = state;
    }

    public Integer toInt() {
        return this.state;
    }
}