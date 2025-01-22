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
    private UserService service = null;

    @Qualifier("userControllerMapperImpl")
    @Autowired
    private UserControllerMapper mapper = null;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.getProfile(userId)));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileRQDTO profile,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.updateProfile(userId, mapper.toIDTO(profile))));
    }

    @GetMapping("/username")
    public ResponseEntity<?> getUsername(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(service.getUsername(userId));
    }

    @PatchMapping("/username")
    public ResponseEntity<?> updateUsername(
            @RequestBody String username,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.updateUsername(userId, username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<?> getEmail(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(service.getEmail(userId));
    }

    @PatchMapping("/email")
    public ResponseEntity<?> updateEmail(
            @Valid @RequestBody String newEmail,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.updateEmail(userId, newEmail);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/picture/{userId}")
    public ResponseEntity<?> getPicture(
            @RequestHeader("x-user-id") String userId
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
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(service.updatePicture(userId, FileUtils.download(picture)).toString());
    }

    @GetMapping("/phonenumber")
    public ResponseEntity<?> getPhoneNumber(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(service.getPhoneNumber(userId));
    }

    @PatchMapping("/phonenumber")
    public ResponseEntity<?> updatePhoneNumber(
            @RequestBody String phoneNumber,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(service.updatePhoneNumber(userId, phoneNumber));
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody String newPassword,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.updatePassword(userId, newPassword);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metadata")
    public ResponseEntity<?> getMetadata(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.getMetadata(userId);

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PatchMapping("/metadata")
    public ResponseEntity<?> updateMetadata(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.updateMetadata(userId, Optional.empty());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/metadata")
    public ResponseEntity<?> setMetadata(
            @RequestHeader("x-user-id") String userId,
            @RequestBody MetadataRQDTO metadata
    ) throws APIException {
        service.updateMetadata(userId, Optional.of(mapper.toIDTO(metadata)));

        return ResponseEntity.noContent().build();
    }
}