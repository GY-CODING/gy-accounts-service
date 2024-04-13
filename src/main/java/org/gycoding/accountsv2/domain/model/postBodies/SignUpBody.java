package org.gycoding.accountsv2.domain.model.postBodies;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SignUpBody {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Builder
    public SignUpBody(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
