package org.gycoding.accounts.infrastructure.api.controller;

import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.application.service.auth.AuthService;
import org.gycoding.accounts.infrastructure.api.dto.in.auth.UserRQDTO;
import org.gycoding.accounts.infrastructure.api.mapper.AuthControllerMapper;
import org.gycoding.quasar.exceptions.model.QuasarException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    private final AuthControllerMapper mapper;

    @PostMapping("/login")
	public ResponseEntity<TokenHolder> login(@Valid @RequestBody UserRQDTO user) throws QuasarException {
        return ResponseEntity.ok(service.login(mapper.toIDTO(user)));
	}

    @PostMapping("/signup")
    public ResponseEntity<CreatedUser> signUp(@Valid @RequestBody UserRQDTO user) throws QuasarException {
        final var createdUser = service.signUp(mapper.toIDTO(user));

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/google")
    public ResponseEntity<String> googleAuth() throws QuasarException {
        return ResponseEntity.ok(service.googleAuth());
    }

    @GetMapping("/google/callback")
    public ResponseEntity<TokenHolder> handleGoogleCallback(@RequestParam String code) throws QuasarException {
        return ResponseEntity.ok(service.handleGoogleResponse(code));
    }
}