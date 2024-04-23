package org.gycoding.accounts.controller;

import org.gycoding.accounts.model.database.AccountService;
import org.gycoding.accounts.model.dto.UserRQDTO;
import org.gycoding.accounts.model.entities.ServerStatus;
import org.gycoding.accounts.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AccountsController {
    @Autowired
    private AccountService accountService = null;

    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRQDTO body) {
        return ResponseEntity.ok(accountService.checkLogin(body.email(), body.password()).toString());
	}

    @PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserRQDTO body) {
        final User user = User.builder()
                .username(body.user())
                .email(body.email())
                .build();

        return ResponseEntity.ok(accountService.signUp(user, body.password()).toString());
	}

    @PostMapping("/session")
	public ResponseEntity<?> getSession(@RequestBody UserRQDTO body) {
        return ResponseEntity.ok(accountService.getSession(body.email(), body.password()).toString());
	}

    @PutMapping("update/username")
    public ResponseEntity<?> updateUsername(@RequestBody UserRQDTO body) {
        return ResponseEntity.ok(accountService.updateUsername(body.email(), body.password(), body.newUsername()).toString());
    }

    @PutMapping("update/email")
    public ResponseEntity<?> updateEmail(@RequestBody UserRQDTO body) {
        return ResponseEntity.ok(accountService.updateEmail(body.email(), body.password(), body.newEmail()).toString());
    }

    @PutMapping("update/password")
    public ResponseEntity<?> updatePassword(@RequestBody UserRQDTO body, @RequestParam Boolean forgotten) {
        if(forgotten) {
            return ResponseEntity.ok(accountService.updatePasswordForgotten(body.email(), body.newPassword()).toString());
        } else {
            return ResponseEntity.ok(accountService.updatePassword(body.email(), body.password(), body.newPassword()).toString());
        }
    }
}