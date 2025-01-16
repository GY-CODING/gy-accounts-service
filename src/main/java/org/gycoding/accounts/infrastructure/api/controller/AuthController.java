package org.gycoding.accounts.infrastructure.api.controller;

import jakarta.validation.Valid;
import org.gycoding.accounts.application.service.auth.AuthService;
import org.gycoding.accounts.infrastructure.api.dto.in.auth.UserRQDTO;
import org.gycoding.accounts.infrastructure.api.mapper.AuthControllerMapper;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service = null;

    @Qualifier("authControllerMapperImpl")
    @Autowired
    private AuthControllerMapper mapper = null;

    @PostMapping("/login")
	public ResponseEntity<?> login(
            @Valid @RequestBody UserRQDTO user
    ) throws APIException {
        return ResponseEntity.ok(service.login(mapper.toIDTO(user)));
	}

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody UserRQDTO user
    ) throws APIException {
        final var createdUser = service.signUp(mapper.toIDTO(user));

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/google")
    public ResponseEntity<?> googleAuth() throws APIException {
        return ResponseEntity.ok(service.googleAuth());
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> handleGoogleCallback(
            @RequestParam String code
    ) throws APIException {
        return ResponseEntity.ok(service.handleGoogleResponse(code));
    }
}