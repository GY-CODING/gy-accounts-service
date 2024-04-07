package org.gycoding.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountsApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AccountsApplication.class);
        app.setRegisterShutdownHook(false);
        app.run(args);
    }
}
