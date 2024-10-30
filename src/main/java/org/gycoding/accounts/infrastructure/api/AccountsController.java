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

@RestController
@RequestMapping("/auth")
public class AccountsController {
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

    @PutMapping("/metadata/setup")
    public ResponseEntity<?> setupMetadata(@RequestHeader String userId) throws APIException {
        metadataService.setupMetadata(userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/usernames/save")
    public ResponseEntity<?> saveUsername(
            @RequestBody UsernameRQDTO body,
            @RequestHeader String userId
    ) throws APIException {
        return ResponseEntity.ok(metadataService.saveUsername(userId, body.username()).toString());
    }

    @PostMapping("/pictures/save")
    public ResponseEntity<?> savePicture(
            @RequestBody MultipartFile picture
    ) throws APIException {
        return ResponseEntity.ok(metadataService.savePicture(picture).toString());
    }

    @GetMapping("/pictures/get")
    public ResponseEntity<?> getPicture(
            @RequestParam("name") String pictureName
    ) throws APIException {
        final var picture = metadataService.getPicture(pictureName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(picture.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + picture.name() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, picture.contentType())
                .body(picture.picture().getData());
    }
}