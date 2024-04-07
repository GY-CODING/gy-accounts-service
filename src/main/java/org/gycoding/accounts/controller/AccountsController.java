package org.gycoding.accounts.controller;

import org.gycoding.accounts.model.database.AccountService;
import org.gycoding.accounts.model.entities.Email;
import org.gycoding.accounts.model.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
    private AccountService accountService;

    @GetMapping("/login")
	public Boolean login(
        @RequestParam(value = "user", defaultValue = "") String user, 
        @RequestParam(value = "password", defaultValue = "") String password
    ) {
        if(user.matches(Email.EMAIL_REGEX)) {
            return accountService.checkLogin(new Email(user), password);
        } else {
            return accountService.checkLogin(user, password);
        }
	}

    @GetMapping("/signup")
	public Integer signUp(
        @RequestParam(value = "username", defaultValue = "") String username,
        @RequestParam(value = "email", defaultValue = "") String email,
        @RequestParam(value = "password", defaultValue = "") String password
    ) {
        return accountService.signUp(new User(username, new Email(email)), password).toInt();
	}

    @GetMapping("/session")
	public String getSession(
        @RequestParam(value = "user", defaultValue = "") String user,
        @RequestParam(value = "password", defaultValue = "") String password
    ) {
        return accountService.getSession(user, password);
	}
}