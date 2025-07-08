package org.gycoding.accounts.infrastructure.api.controller.products;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.application.service.products.books.BooksService;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.books.FriendRequestRQDTO;
import org.gycoding.accounts.infrastructure.api.mapper.UserControllerMapper;
import org.gycoding.accounts.infrastructure.api.mapper.products.BooksControllerMapper;
import org.gycoding.exceptions.model.APIException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BooksController {
    private final BooksService service;

    private final BooksControllerMapper booksMapper;

    private final UserControllerMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<?> listUsers(
            @RequestHeader("x-user-id") String userId,
            @RequestParam String query
    ) throws APIException {
        return ResponseEntity.ok(
                service.listUsers(userId, query).stream()
                        .map(userMapper::toPublicRSDTO)
                        .toList()
        );
    }

    @GetMapping("/users/public")
    public ResponseEntity<?> listUsersPublic(
            @RequestParam String query
    ) throws APIException {
        return ResponseEntity.ok(
                service.listUsers(query).stream()
                        .map(userMapper::toPublicRSDTO)
                        .toList()
        );
    }

    @GetMapping("/friends")
    public ResponseEntity<?> listFriends(
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(
                service.listFriends(userId).stream()
                        .map(userMapper::toPublicRSDTO)
                        .toList()
        );
    }

    @PostMapping("/friends/request")
	public ResponseEntity<?> sendFriendRequest(
            @Valid @RequestBody FriendRequestRQDTO request,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        return ResponseEntity.ok(booksMapper.toRSDTO(service.sendFriendRequest(userId, request.to())));
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
}