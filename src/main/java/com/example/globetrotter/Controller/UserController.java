package com.example.globetrotter.Controller;

import com.example.globetrotter.Model.User;
import com.example.globetrotter.Service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String username) {
        return ResponseEntity.ok(userService.registerUser(username));
    }

    @GetMapping("/check-user")
    public ResponseEntity<Map<String, Boolean>> checkUser(@RequestParam String username) {
        boolean exists = userService.checkUserExists(username);
        return ResponseEntity.ok(Map.of("exists", exists));
    }


    @GetMapping("/invite-details")
    public ResponseEntity<Map<String, String>> getInviteDetails(@RequestParam String username) {
        return ResponseEntity.ok(userService.getInviteDetails(username));
    }

    @GetMapping("/score")
    public ResponseEntity<Map<String, Integer>> getScoreByInviteCode(@RequestParam String inviteCode) {
        int score = userService.getScoreByInviteCode(inviteCode);
        return ResponseEntity.ok(Map.of("score", score));
    }
}
