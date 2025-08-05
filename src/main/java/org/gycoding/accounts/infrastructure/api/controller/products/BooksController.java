package org.gycoding.accounts.infrastructure.api.controller.products;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.application.service.products.books.BooksService;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books.ActivityRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books.BiographyRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books.FriendRequestRQDTO;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books.HallOfFameRQDTO;
import org.gycoding.accounts.infrastructure.api.mapper.UserControllerMapper;
import org.gycoding.accounts.infrastructure.api.mapper.products.BooksControllerMapper;
import org.gycoding.exceptions.model.APIException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BooksController {
    private final BooksService service;

    private final BooksControllerMapper mapper;

    @GetMapping("/{profileId}/public")
    public ResponseEntity<?> getProfilePublic(
            @PathVariable String profileId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toPublicRSDTO(service.getProfile(UUID.fromString(profileId))));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<?> getProfile(
            @RequestHeader("x-user-id") String userId,
            @PathVariable String profileId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.getProfile(userId, UUID.fromString(profileId))));
    }

    @GetMapping("/friends")
    public ResponseEntity<?> listFriends(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(
                service.listFriends(userId).stream()
                        .map(mapper::toPublicRSDTO)
                        .toList()
        );
    }

    @PostMapping("/friends/request")
	public ResponseEntity<?> sendFriendRequest(
            @Valid @RequestBody FriendRequestRQDTO request,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.sendFriendRequest(userId, request.to())));
	}

    @GetMapping("/friends/request")
    public ResponseEntity<?> listFriendRequests(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(
                service.listFriendRequests(userId).stream()
                        .map(mapper::toRSDTO)
                        .toList()
        );
    }

    @PostMapping("/friends/manage")
    public ResponseEntity<?> manageFriendRequest(
            @Valid @RequestBody FriendRequestRQDTO request,
            @RequestParam String requestId,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.manageFriendRequest(userId, requestId, request.command());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/friends/{id}")
    public ResponseEntity<?> removeFriend(
            @RequestHeader("x-user-id") String userId,
            @PathVariable("id") String friendProfileId
    ) throws APIException {
        service.removeFriend(userId, UUID.fromString(friendProfileId));

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/biography")
    public ResponseEntity<?> updateBiography(
            @Valid @RequestBody BiographyRQDTO biography,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(service.updateBiography(userId, biography.biography()));
    }

    @GetMapping("/{profileId}/halloffame")
    public ResponseEntity<?> getHallOfFame(
            @PathVariable String profileId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.getHallOfFame(UUID.fromString(profileId))));
    }

    @PatchMapping("/halloffame")
    public ResponseEntity<?> setHallOfFame(
            @Valid @RequestBody HallOfFameRQDTO hallOfFame,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.setHallOfFame(userId, mapper.toIDTO(hallOfFame))));
    }

    @DeleteMapping("/halloffame/book/{bookId}")
    public ResponseEntity<?> removeBookFromHallOfFame(
            @PathVariable("bookId") String bookId,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.removeBookFromHallOfFame(userId, bookId)));
    }

    @GetMapping("/{profileId}/activity")
    public ResponseEntity<?> listActivities(
            @PathVariable String profileId
    ) throws APIException {
        return ResponseEntity.ok(
                service.listActivities(UUID.fromString(profileId)).stream()
                        .map(mapper::toRSDTO)
                        .toList()
        );
    }

    @PostMapping("/activity")
    public ResponseEntity<?> setActivity(
            @Valid @RequestBody ActivityRQDTO activity,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(mapper.toRSDTO(service.setActivity(userId, mapper.toIDTO(activity))));
    }
}
