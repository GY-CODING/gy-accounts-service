package org.gycoding.accounts.infrastructure.api;

import org.gycoding.accounts.application.service.auth.AuthRepository;
import org.gycoding.accounts.application.service.auth.AuthService;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountsController {

    @Autowired
    private AuthRepository authService = null;

    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRQDTO body) throws AccountsAPIException {
        return ResponseEntity.ok(authService.login(body.email(), body.password()));
	}

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRQDTO body) throws AccountsAPIException {
        final var createdUser = authService.signUp(body.email(), body.user(), body.password());

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/google")
    public ResponseEntity<?> googleAuth() throws AccountsAPIException {
        return ResponseEntity.ok(authService.googleAuth());
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam("code") String code) throws AccountsAPIException {
        return ResponseEntity.ok(authService.handleGoogleResponse(code));
    }

    @PutMapping("/metadata/set")
    public ResponseEntity<?> setMetadata(
            @RequestHeader String jwt,
            @RequestParam Boolean isReset
    ) throws AccountsAPIException {
        final var userId = authService.decode(jwt);
        authService.setMetadata(userId, isReset);

        return ResponseEntity.noContent().build();
    }
}