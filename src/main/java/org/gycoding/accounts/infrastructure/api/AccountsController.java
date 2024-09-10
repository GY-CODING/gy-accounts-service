package org.gycoding.accounts.infrastructure.api;

import jakarta.validation.Valid;
import org.gycoding.accounts.application.service.auth.AuthRepository;
import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountsController {
    @Autowired
    private AuthRepository authService = null;
    @Autowired
    private AuthFacade authFacade = null;

    @PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserRQDTO body) throws APIException {
        return ResponseEntity.ok(authService.login(body.email(), body.password()));
	}

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserRQDTO body) throws APIException {
        final var createdUser = authService.signUp(body.email(), body.user(), body.password());

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/google")
    public ResponseEntity<?> googleAuth() throws APIException {
        return ResponseEntity.ok(authService.googleAuth());
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam("code") String code) throws APIException {
        return ResponseEntity.ok(authService.handleGoogleResponse(code));
    }

    @PutMapping("/metadata/set")
    public ResponseEntity<?> setMetadata(
            @RequestHeader String userId,
            @RequestParam Boolean isReset
    ) throws APIException {
        authService.setMetadata(userId, isReset);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/metadata/refresh")
    public ResponseEntity<?> refreshMetadata(@RequestHeader String userId) throws APIException {
        authService.refreshMetadata(userId);

        return ResponseEntity.noContent().build();
    }
}