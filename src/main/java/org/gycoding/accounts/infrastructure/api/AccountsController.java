package org.gycoding.accounts.infrastructure.api;

import jakarta.validation.Valid;
import org.gycoding.accounts.application.service.auth.AuthRepository;
import org.gycoding.accounts.application.service.auth.MetadataRepository;
import org.gycoding.accounts.domain.entities.metadata.GYCODINGRoles;
import org.gycoding.accounts.domain.entities.metadata.UserMetadata;
import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.gycoding.accounts.infrastructure.dto.UsernameRQDTO;
import org.gycoding.accounts.infrastructure.dto.metadata.MetadataRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AccountsController {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private AuthRepository authService = null;

    @Autowired
    private MetadataRepository metadataService = null;

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

    @PostMapping("/update/username")
    public ResponseEntity<?> updateUsername(
            @RequestBody UsernameRQDTO body,
            @RequestHeader String userId
    ) throws APIException {
        return ResponseEntity.ok(authService.updateUsername(userId, body.username()).toString());
    }

    @PutMapping("/update/email")
    public ResponseEntity<?> updateEmail(
            @Valid @RequestBody String newEmail,
            @RequestHeader String token
    ) throws APIException {
        authService.updateEmail(authFacade.decode(token), newEmail);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody String newPassword,
            @RequestHeader String token
    ) throws APIException {
        authService.updatePassword(authFacade.decode(token), newPassword);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/picture")
    public ResponseEntity<?> updatePicture(
            @RequestBody MultipartFile picture,
            @RequestHeader String userId
    ) throws APIException {
        return ResponseEntity.ok(authService.updatePicture(userId, picture).toString());
    }

    @PutMapping("/update/metadata")
    public ResponseEntity<?> updateMetadata(@RequestHeader String userId) throws APIException {
        metadataService.updateMetadata(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/picture")
    public ResponseEntity<?> getPicture(
            @RequestHeader String token
    ) throws APIException {
        final var picture = authService.getPicture(authFacade.decode(token));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(picture.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + picture.name() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, picture.contentType())
                .body(picture.picture().getData());
    }
}