package org.gycoding.accounts.infrastructure.api;

import jakarta.validation.Valid;
import org.gycoding.accounts.application.service.auth.AuthRepository;
import org.gycoding.accounts.application.service.username.UsernameRepository;
import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.gycoding.accounts.infrastructure.dto.UsernameRQDTO;
import org.gycoding.accounts.infrastructure.external.auth.AuthFacade;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/usernames")
public class UsernameController {
    @Autowired
    private UsernameRepository usernameService = null;

    @Autowired
    private AuthFacade authFacade = null;

    @PostMapping("/save")
	public ResponseEntity<?> save(
            @RequestBody UsernameRQDTO body,
            @RequestHeader String userId
    ) throws APIException {
        return ResponseEntity.ok(usernameService.save(userId, body.username()).toString());
	}

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String username) throws APIException {
        usernameService.delete(username);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String username) throws APIException {
        return ResponseEntity.ok(usernameService.getUsername(username).toString());
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() throws APIException {
        return ResponseEntity.ok(usernameService.listUsernames().toString());
    }
}