package org.gycoding.accounts.infrastructure.api;

import jakarta.validation.Valid;
import org.gycoding.accounts.application.service.gymessages.MessagesRepository;
import org.gycoding.accounts.infrastructure.dto.ChatRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    private MessagesRepository messagesService = null;
    @Autowired
    private AuthFacade authFacade = null;

    @PutMapping("/chat/add")
	public ResponseEntity<?> addChat(
            @Valid
            @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader String token
    ) throws APIException {
        messagesService.addChat(authFacade.decode(token), chatRQDTO);
        return ResponseEntity.noContent().build();
	}

    @DeleteMapping("/chat/remove")
    public ResponseEntity<?> removeChatWithUserID(
            @Valid
            @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader String token
    ) throws APIException {
        messagesService.removeChat(authFacade.decode(token), chatRQDTO.chatId());
        return ResponseEntity.noContent().build();
    }
  
    @PutMapping("/chat/set")
    public ResponseEntity<?> setAdminInChat(
            @Valid
            @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader String token
    ) throws APIException {
        messagesService.setAdmin(authFacade.decode(token), chatRQDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/chat/list")
    public ResponseEntity<?> listChats(
            @RequestHeader String token
    ) throws APIException {
        return ResponseEntity.ok(messagesService.listChats(authFacade.decode(token)));
    }
}