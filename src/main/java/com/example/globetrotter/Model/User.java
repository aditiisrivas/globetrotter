package com.example.globetrotter.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private int score;
    private String inviteCode; // Unique invite code

    public void generateInviteCode() {
        this.inviteCode = UUID.randomUUID().toString().substring(0, 8); // Generates an 8-character code
    }
}
