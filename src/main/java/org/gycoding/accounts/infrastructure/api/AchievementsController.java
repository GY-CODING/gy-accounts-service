package org.gycoding.accounts.infrastructure.api;

import org.gycoding.accounts.application.service.achievements.AchievementsService;
import org.gycoding.accounts.application.service.auth.AuthService;
import org.gycoding.accounts.infrastructure.dto.UserRQDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/achievements")
public class AchievementsController {
    @Autowired
    private AuthService authService = null;

    @Autowired
    private AchievementsService achievementsService = null;

    @PutMapping("/unlock")
	public ResponseEntity<?> unlock(
            @RequestParam("id") Integer achievementID,
            @RequestBody String jwt
    ) throws Exception {
        final var userId = authService.decode(jwt);
        System.out.println(userId);
        achievementsService.unlockAchievement(userId, achievementID);

        return ResponseEntity.noContent().build();
	}
}