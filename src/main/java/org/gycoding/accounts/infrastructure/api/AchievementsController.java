package org.gycoding.accounts.infrastructure.api;

import org.gycoding.accounts.application.service.achievements.AchievementsService;
import org.gycoding.accounts.application.service.auth.AuthService;
import org.gycoding.accounts.domain.exceptions.AccountsAPIException;
import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/achievements")
public class AchievementsController {
    @Autowired
    private AuthService authService = null;

    @Autowired
    private AchievementsService achievementsService = null;

    @GetMapping("/")
    public ResponseEntity<?> listAchievementsByUser(
            @RequestHeader String jwt
    ) throws AccountsAPIException {
        return ResponseEntity.ok(achievementsService.listAchievements(authService.decode(jwt)));
    }

    @PutMapping("/unlock")
	public ResponseEntity<?> unlock(
            @RequestParam("id") Integer achievementID,
            @RequestHeader String jwt
    ) throws AccountsAPIException {
        final var userId = authService.decode(jwt);
        achievementsService.unlockAchievement(userId, achievementID);

        return ResponseEntity.noContent().build();
	}

    @PutMapping("/reset")
    public ResponseEntity<?> resetAchievements(
            @RequestHeader String jwt
    ) throws AccountsAPIException {
        final var userId = authService.decode(jwt);
        achievementsService.resetAchievements(userId);

        return ResponseEntity.noContent().build();
    }
}