package org.gycoding.accounts.infrastructure.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.application.dto.out.user.metadata.ProfileODTO;
import org.gycoding.accounts.application.service.user.UserService;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.ProfileRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.TransformIDRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.MetadataRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.ProfileRSDTO;
import org.gycoding.accounts.infrastructure.api.dto.out.user.metadata.PublicProfileRSDTO;
import org.gycoding.accounts.infrastructure.api.mapper.UserControllerMapper;
import org.gycoding.accounts.shared.utils.FileUtils;
import org.gycoding.quasar.exceptions.model.QuasarException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    private final UserControllerMapper mapper;

    @GetMapping("/list")
    public ResponseEntity<List<PublicProfileRSDTO>> listUsers(
            @RequestHeader("x-user-id") String userId,
            @RequestParam String query
    ) throws QuasarException {
        return ResponseEntity.ok(
                service.listUsers(userId, query).stream()
                        .map(mapper::toPublicRSDTO)
                        .toList()
        );
    }

    @GetMapping("/list/public")
    public ResponseEntity<List<PublicProfileRSDTO>> listUsersPublic(
            @RequestParam String query
    ) throws QuasarException {
        return ResponseEntity.ok(
                service.listUsers(query).stream()
                        .map(mapper::toPublicRSDTO)
                        .toList()
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileRSDTO> getProfile(
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(mapper.toRSDTO(service.getProfile(userId)));
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileRSDTO> updateProfile(
            @Valid @RequestBody ProfileRQDTO profile,
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(mapper.toRSDTO(service.updateProfile(userId, mapper.toIDTO(profile))));
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(service.getUsername(userId));
    }

    @PatchMapping("/username")
    public ResponseEntity<String> updateUsername(
            @RequestBody String username,
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(service.updateUsername(userId, username));
    }

    @GetMapping("/email")
    public ResponseEntity<String> getEmail(
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(service.getEmail(userId));
    }

    @GetMapping("/picture/{userId}")
    public ResponseEntity<?> getPicture(
            @PathVariable("userId") String userId
    ) throws QuasarException {
        final var picture = service.getPicture(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(picture.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + picture.name() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, picture.contentType())
                .body(picture.picture().getData());
    }

    @PatchMapping("/picture")
    public ResponseEntity<Void> updatePicture(
            @RequestBody String picture,
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        service.updatePicture(userId, FileUtils.read(picture));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/phonenumber")
    public ResponseEntity<String> getPhoneNumber(
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(service.getPhoneNumber(userId));
    }

    @PatchMapping("/phonenumber")
    public ResponseEntity<String> updatePhoneNumber(
            @RequestBody String phoneNumber,
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(service.updatePhoneNumber(userId, phoneNumber));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody String newPassword,
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        service.updatePassword(userId, newPassword);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metadata")
    public ResponseEntity<MetadataRSDTO> getMetadata(
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(mapper.toRSDTO(service.getMetadata(userId)));
    }

    @PostMapping("/metadata")
    public ResponseEntity<MetadataRSDTO> syncMetadata(
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(mapper.toRSDTO(service.syncMetadata(userId)));
    }

    @PatchMapping("/metadata/apikey")
    public ResponseEntity<String> refreshApiKey(
            @RequestHeader("x-user-id") String userId
    ) throws QuasarException {
        return ResponseEntity.ok(service.refreshApiKey(userId));
    }

    @PostMapping("/transform/userId")
    public ResponseEntity<String> getUserId(
            @RequestBody TransformIDRQDTO transform
    ) throws QuasarException {
        return ResponseEntity.ok(service.transform(UUID.fromString(transform.profileId())));
    }

    @PostMapping("/transform/profileId")
    public ResponseEntity<String> getProfileId(
            @Valid @RequestBody TransformIDRQDTO transform
    ) throws QuasarException {
        return ResponseEntity.ok(service.transform(transform.userId()).toString());
    }
}