package org.gycoding.accounts.infrastructure.api.controller.products;

import jakarta.validation.Valid;
import org.gycoding.accounts.application.service.products.gymessages.MessagesService;
import org.gycoding.accounts.infrastructure.api.dto.in.user.metadata.gymessages.ChatRQDTO;
import org.gycoding.accounts.domain.repository.AuthFacade;
import org.gycoding.accounts.infrastructure.api.mapper.products.MessagesControllerMapper;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    private AuthFacade authFacade = null;

    @Autowired
    private MessagesService service = null;

    @Autowired
    private MessagesControllerMapper mapper = null;

    @PatchMapping("/chats/add")
	public ResponseEntity<?> addChat(
            @Valid
            @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader String Authorization
    ) throws APIException {
        service.addChat(authFacade.decode(Authorization), mapper.toIDTO(chatRQDTO));
        return ResponseEntity.noContent().build();
	}

    @DeleteMapping("/chats")
    public ResponseEntity<?> removeChat(
            @Valid
            @RequestParam String chatId,
            @RequestHeader String Authorization
    ) throws APIException {
        service.removeChat(authFacade.decode(Authorization), chatId);
        return ResponseEntity.noContent().build();
    }
  
    @PutMapping("/chats/admin")
    public ResponseEntity<?> setAdminInChat(
            @Valid
            @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader String Authorization
    ) throws APIException {
        service.setAdmin(authFacade.decode(Authorization), mapper.toIDTO(chatRQDTO));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/chats")
    public ResponseEntity<?> listChats(
            @RequestHeader String Authorization
    ) throws APIException {
        return ResponseEntity.ok(service.listChats(authFacade.decode(Authorization)));
    }
}