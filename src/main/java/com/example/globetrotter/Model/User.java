package com.example.globetrotter.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private int score;


    private String inviteCode; // Unique invite code

    public void generateInviteCode() {
        this.inviteCode = UUID.randomUUID().toString().substring(0, 8); // Generates an 8-character code
    }
}
