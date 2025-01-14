package org.gycoding.accounts.infrastructure.api;

import jakarta.validation.Valid;
import org.gycoding.accounts.application.service.auth.AuthRepository;
import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.gycoding.accounts.infrastructure.dto.UsernameRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AccountsController {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private AuthRepository authService = null;

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

    @PutMapping("/update/username")
    public ResponseEntity<?> updateUsername(
            @RequestBody String username,
            @RequestHeader String Authorization
    ) throws APIException {
        authService.updateUsername(authFacade.decode(Authorization), username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/email")
    public ResponseEntity<?> updateEmail(
            @Valid @RequestBody String newEmail,
            @RequestHeader String Authorization
    ) throws APIException {
        authService.updateEmail(authFacade.decode(Authorization), newEmail);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody String newPassword,
            @RequestHeader String Authorization
    ) throws APIException {
        authService.updatePassword(authFacade.decode(Authorization), newPassword);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/picture")
    public ResponseEntity<?> updatePicture(
            @RequestBody MultipartFile picture,
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(authService.updatePicture(authFacade.decode(Authorization), picture).toString());
    }

    @PutMapping("/update/metadata")
    public ResponseEntity<?> updateMetadata(@RequestHeader String userId) throws APIException {
        authService.updateMetadata(userId, Optional.empty());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/picture/{userId}")
    public ResponseEntity<?> getPicture(
            @PathVariable String userId
    ) throws APIException {
        final var picture = authService.getPicture(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(picture.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + picture.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, picture.getContentType())
                .body(picture.getPicture().getData());
    }

    @GetMapping("/get/profile")
    public ResponseEntity<?> getUserProfile(
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(authService.getUserProfile(authFacade.decode(Authorization)));
    }
}