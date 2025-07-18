package org.gycoding.accounts.infrastructure.api.controller.products;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gycoding.accounts.application.service.products.messages.MessagesService;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.messages.ChatRQDTO;
import org.gycoding.accounts.infrastructure.api.mapper.products.MessagesControllerMapper;
import org.gycoding.exceptions.model.APIException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/messages")
public class MessagesController {
    private final MessagesService service;

    private final MessagesControllerMapper mapper;

    @PatchMapping("/chats/add")
	public ResponseEntity<?> addChat(
            @Valid @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.addChat(userId, mapper.toIDTO(chatRQDTO));
        return ResponseEntity.noContent().build();
	}

    @DeleteMapping("/chats")
    public ResponseEntity<?> removeChat(
            @Valid @RequestParam String chatId,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.removeChat(userId, chatId);
        return ResponseEntity.noContent().build();
    }
  
    @PutMapping("/chats/admin")
    public ResponseEntity<?> setAdminInChat(
            @Valid @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader("x-user-id") String userId
    ) throws APIException {
        service.setAdmin(userId, mapper.toIDTO(chatRQDTO));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/chats")
    public ResponseEntity<?> listChats(@RequestHeader("x-user-id") String userId) throws APIException {
        return ResponseEntity.ok(service.listChats(userId).stream().map(mapper::toRSDTO));
    }
}