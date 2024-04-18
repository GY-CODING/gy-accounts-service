package org.gycoding.accounts.controller;

import org.gycoding.accounts.model.database.AccountService;
import org.gycoding.accounts.model.dto.UserRQDTO;
import org.gycoding.accounts.model.entities.Email;
import org.gycoding.accounts.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AccountsController {
    private AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    // TODO. Every endpoint of this controller must use its corresponding method in the ControllerMapper class to parse the request body.

    @PostMapping("/login")
	public Boolean login(@RequestBody UserRQDTO body) {
        if(body.user().matches(Email.EMAIL_REGEX)) {
            return accountService.checkLogin(new Email(body.user()), body.password());
        } else {
            return accountService.checkLogin(body.user(), body.password());
        }
	}

    @PostMapping("/signup")
	public Integer signUp(@RequestBody UserRQDTO body) {
        return accountService.signUp(new User(body.user(), new Email(body.email())), body.password()).toInt();
	}

    @PostMapping("/session")
	public String getSession(@RequestBody UserRQDTO body) {
        if(body.user().matches(Email.EMAIL_REGEX)) {
            return accountService.getSession(new Email(body.user()), body.password()).toString();
        } else {
            return accountService.getSession(body.user(), body.password()).toString();
        }
	}

    @PutMapping("update/username")
    public Integer updateUsername(@RequestBody UserRQDTO body) {
        if(body.user().matches(Email.EMAIL_REGEX)) {
            return accountService.updateUsername(new Email(body.user()), body.password(), body.newUsername()).toInt();
        } else {
            return accountService.updateUsername(body.user(), body.password(), body.newUsername()).toInt();
        }
    }

    @PutMapping("update/email")
    public Integer updateEmail(@RequestBody UserRQDTO body) {
        if(body.user().matches(Email.EMAIL_REGEX)) {
            return accountService.updateEmail(new Email(body.user()), body.password(), new Email(body.newEmail())).toInt();
        } else {
            return accountService.updateEmail(body.user(), body.password(), new Email(body.newEmail())).toInt();
        }
    }

    @PutMapping("update/password")
    public Integer updatePassword(@RequestBody UserRQDTO body, @RequestParam Boolean forgotten) {
        if(forgotten) {
            if(body.user().matches(Email.EMAIL_REGEX)) {
                return accountService.updatePasswordForgotten(new Email(body.user()), body.newPassword()).toInt();
            } else {
                return accountService.updatePasswordForgotten(body.user(), body.newPassword()).toInt();
            }
        } else {
            if(body.user().matches(Email.EMAIL_REGEX)) {
                return accountService.updatePassword(new Email(body.user()), body.password(), body.newPassword()).toInt();
            } else {
                return accountService.updatePassword(body.user(), body.password(), body.newPassword()).toInt();
            }
        }
    }
}