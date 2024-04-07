package org.gycoding.accounts.controller;

import org.gycoding.accounts.model.database.AccountService;
import org.gycoding.accounts.model.entities.Email;
import org.gycoding.accounts.model.entities.User;
import org.gycoding.accounts.model.postBodies.LogInBody;
import org.gycoding.accounts.model.postBodies.SignUpBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountsController {
    private AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
	public Boolean login(@RequestBody LogInBody logInBody) {
        if(logInBody.getUser().matches(Email.EMAIL_REGEX)) {
            return accountService.checkLogin(new Email(logInBody.getUser()), logInBody.getPassword());
        } else {
            return accountService.checkLogin(logInBody.getUser(), logInBody.getPassword());
        }
	}

    @PostMapping("/signup")
	public Integer signUp(@RequestBody SignUpBody signUpBody) {
        return accountService.signUp(new User(signUpBody.getUsername(), new Email(signUpBody.getEmail())), signUpBody.getPassword()).toInt();
	}

    @PostMapping("/session")
	public String getSession(@RequestBody LogInBody logInBody) {
        if(logInBody.getUser().matches(Email.EMAIL_REGEX)) {
            return accountService.getSession(new Email(logInBody.getUser()), logInBody.getPassword());
        } else {
            return accountService.getSession(logInBody.getUser(), logInBody.getPassword());
        }
	}
}