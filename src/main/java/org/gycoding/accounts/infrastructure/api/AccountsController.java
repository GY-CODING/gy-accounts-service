package org.gycoding.accounts.infrastructure.api;

import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountsController {

    @Autowired
    private AuthFacadeImpl authService = null;

    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRQDTO body) throws Exception {
        return ResponseEntity.ok(authService.login(body.email(), body.password()));
	}

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRQDTO body) throws Exception {
        return ResponseEntity.ok(authService.signUp(body.email(), body.user(), body.password()));
    }

    @GetMapping("/google")
    public ResponseEntity<?> googleAuth() {
        return ResponseEntity.ok(authService.googleAuth());
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam("code") String code) throws Exception {
        return ResponseEntity.ok(authService.handleGoogleResponse(code));
    }
}