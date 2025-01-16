package org.gycoding.accounts.infrastructure.api.controller;

import jakarta.validation.Valid;
import org.gycoding.accounts.application.service.user.UserService;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.infrastructure.api.dto.in.user.ProfileRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.MetadataRQDTO;
import org.gycoding.accounts.infrastructure.external.utils.FileUtils;
import org.gycoding.accounts.infrastructure.api.mapper.UserControllerMapper;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private UserService service = null;

    @Qualifier("userControllerMapperImpl")
    @Autowired
    private UserControllerMapper mapper = null;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(service.getProfile(authFacade.decode(Authorization)));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileRQDTO profile,
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(service.updateProfile(authFacade.decode(Authorization), mapper.toIDTO(profile)).toString());
    }

    @GetMapping("/username")
    public ResponseEntity<?> getUsername(
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(service.getUsername(authFacade.decode(Authorization)));
    }

    @PatchMapping("/username")
    public ResponseEntity<?> updateUsername(
            @RequestBody String username,
            @RequestHeader String Authorization
    ) throws APIException {
        service.updateUsername(authFacade.decode(Authorization), username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<?> getEmail(
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(service.getEmail(authFacade.decode(Authorization)));
    }

    @PatchMapping("/email")
    public ResponseEntity<?> updateEmail(
            @Valid @RequestBody String newEmail,
            @RequestHeader String Authorization
    ) throws APIException {
        service.updateEmail(authFacade.decode(Authorization), newEmail);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/picture/{userId}")
    public ResponseEntity<?> getPicture(
            @PathVariable String userId
    ) throws APIException {
        final var picture = service.getPicture(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(picture.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + picture.name() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, picture.contentType())
                .body(picture.picture().getData());
    }

    @PatchMapping("/picture")
    public ResponseEntity<?> updatePicture(
            @RequestBody String picture,
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(service.updatePicture(authFacade.decode(Authorization), FileUtils.download(picture)).toString());
    }

    @GetMapping("/phonenumber")
    public ResponseEntity<?> getPhoneNumber(
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(service.getPhoneNumber(authFacade.decode(Authorization)));
    }

    @PatchMapping("/phonenumber")
    public ResponseEntity<?> updatePhoneNumber(
            @RequestBody String phoneNumber,
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(service.updatePhoneNumber(authFacade.decode(Authorization), phoneNumber));
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody String newPassword,
            @RequestHeader String Authorization
    ) throws APIException {
        service.updatePassword(authFacade.decode(Authorization), newPassword);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metadata")
    public ResponseEntity<?> getMetadata(
            @RequestHeader String Authorization
    ) throws APIException {
        service.getMetadata(authFacade.decode(Authorization));

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PatchMapping("/metadata")
    public ResponseEntity<?> updateMetadata(
            @RequestHeader String userId
    ) throws APIException {
        service.updateMetadata(userId, Optional.empty());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/metadata")
    public ResponseEntity<?> setMetadata(
            @RequestHeader String userId,
            @RequestBody MetadataRQDTO metadata
    ) throws APIException {
        service.updateMetadata(userId, Optional.of(mapper.toIDTO(metadata)));

        return ResponseEntity.noContent().build();
    }
}