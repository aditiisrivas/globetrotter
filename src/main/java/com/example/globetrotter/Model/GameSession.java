package com.example.globetrotter.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "game_sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameSession {
    private String id;
    private String userId;
    private String destinationId;
    private boolean isCompleted;
}
