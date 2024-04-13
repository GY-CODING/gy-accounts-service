package org.gycoding.accountsv2.domain.model.postBodies;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class LogInBody {
    @Getter
    @Setter
    private String user;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Builder
    public LogInBody(String user, String password) {
        this.user = user;
        this.password = password;
    }
}