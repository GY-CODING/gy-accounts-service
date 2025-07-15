package org.gycoding.accounts.infrastructure.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.application.service.user.UserService;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.ProfileRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.TransformIDRQDTO;
import org.gycoding.accounts.infrastructure.api.mapper.UserControllerMapper;
import org.gycoding.accounts.shared.utils.FileUtils;
import org.gycoding.exceptions.model.APIException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    private final UserControllerMapper mapper;

    @GetMapping("/list")
    public ResponseEntity<?> listUsers(
            @RequestHeader("x-user-id") String userId,
            @RequestParam String query
    ) throws APIException {
        return ResponseEntity.ok(
                service.listUsers(userId, query).stream()
                        .map(mapper::toPublicRSDTO)
                        .toList()
        );
    }

    @GetMapping("/list/public")
    public ResponseEntity<?> listUsersPublic(
            @RequestParam String query
    ) throws APIException {
        return ResponseEntity.ok(
                service.listUsers(query).stream()
                        .map(mapper::toPublicRSDTO)
                        .toList()
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.getProfile(userId)));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @Valid @RequestBody ProfileRQDTO profile,
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
        return ResponseEntity.ok(service.updateUsername(userId, username));
    }

    @GetMapping("/email")
    public ResponseEntity<?> getEmail(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(service.getEmail(userId));
    }

    @GetMapping("/picture/{userId}")
    public ResponseEntity<?> getPicture(
            @PathVariable("userId") String userId
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
        return ResponseEntity.ok(service.updatePicture(userId, FileUtils.read(picture)).toString());
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
        return ResponseEntity.ok(service.getMetadata(userId));
    }

    @PostMapping("/metadata")
    public ResponseEntity<?> initMetadata(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.initMetadata(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metadata/apikey/decode")
    public ResponseEntity<?> decodeApiKey(
            @RequestParam String key
    ) throws APIException {
        return ResponseEntity.ok(service.decodeApiKey(key));
    }

    @PatchMapping("/metadata/apikey")
    public ResponseEntity<?> refreshApiKey(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(service.refreshApiKey(userId));
    }

    @PostMapping("/transform/userId")
    public ResponseEntity<?> getUserId(
            @RequestBody TransformIDRQDTO transform
    ) throws APIException {
        return ResponseEntity.ok(service.transform(UUID.fromString(transform.profileId())));
    }

    @PostMapping("/transform/profileId")
    public ResponseEntity<?> getProfileId(
            @Valid @RequestBody TransformIDRQDTO transform
    ) throws APIException {
        return ResponseEntity.ok(service.transform(transform.userId()));
    }
}