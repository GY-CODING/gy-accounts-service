package org.gycoding.accountsv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountsV2Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AccountsV2Application.class);
        app.setRegisterShutdownHook(false);
        app.run(args);
    }
}
