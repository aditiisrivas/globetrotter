package com.example.globetrotter.Controller;

import com.example.globetrotter.Model.User;
import com.example.globetrotter.Service.GameService;
import com.example.globetrotter.repository.DestinationRepository;
import com.example.globetrotter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api/game")

@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping("/next-question")
    public ResponseEntity<Map<String, Object>> getNextQuestion() {
        return ResponseEntity.ok(gameService.getNextQuestion());
    }




    @PostMapping("/validate-answer")
    public ResponseEntity<Map<String, Object>> validateAnswer(@RequestBody Map<String, String> request) {
        UUID destinationId = UUID.fromString(request.get("destinationId")); // ✅ Parse UUID
        String answer = request.get("answer");
        return ResponseEntity.ok(gameService.validateAnswer(destinationId, answer));
    }




    @PostMapping("/track-score")
    public ResponseEntity<Map<String, Integer>> updateScore(@RequestBody Map<String, Object> request) {
        try {

            String username = (String) request.get("username");
            boolean correct = (Boolean) request.get("correct");


            if (username == null || username.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", -1)); // Indicating error with -1 score
            }


            int score = gameService.updateScore(username, correct);


            return ResponseEntity.ok(Map.of("score", score));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", -1));
        }
    }

}
