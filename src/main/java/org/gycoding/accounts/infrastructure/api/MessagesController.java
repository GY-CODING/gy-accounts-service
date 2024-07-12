package org.gycoding.accounts.infrastructure.api;

import org.gycoding.accounts.application.service.auth.AuthService;
import org.gycoding.accounts.application.service.gymessages.MessagesRepository;
import org.gycoding.accounts.application.service.gymessages.MessagesService;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.dto.ChatRQDTO;
import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/messages")
public class MessagesController {
    @Autowired
    private MessagesRepository messagesService = null;
    @Autowired
    private AuthFacade authFacade = null;

    @PutMapping("/chat/add")
	public ResponseEntity<?> addChat(
            @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader String jwt
    ) throws AccountsAPIException {
        messagesService.addChat(authFacade.decode(jwt), chatRQDTO);
        return ResponseEntity.noContent().build();
	}

//    @DeleteMapping("/chat/remove")
//    public ResponseEntity<?> removeChatWithJWT(
//            @RequestBody ChatRQDTO chatRQDTO,
//            @RequestHeader String jwt
//    ) throws AccountsAPIException {
//        messagesService.removeChat(authFacade.decode(jwt), chatRQDTO.chatId());
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/chat/remove")
    public ResponseEntity<?> removeChatWithUserID(
            @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader String userId
    ) throws AccountsAPIException {
        messagesService.removeChat(userId, chatRQDTO.chatId());
        return ResponseEntity.noContent().build();
    }
  
    @PutMapping("/chat/set")
    public ResponseEntity<?> setAdminInChat(
            @RequestBody ChatRQDTO chatRQDTO,
            @RequestHeader String jwt
    ) throws AccountsAPIException {
        messagesService.setAdmin(authFacade.decode(jwt), chatRQDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/chat/list")
    public ResponseEntity<?> listChats(
            @RequestHeader String jwt
    ) throws AccountsAPIException {

        return ResponseEntity.ok(messagesService.listChats(authFacade.decode(jwt)));
    }
}