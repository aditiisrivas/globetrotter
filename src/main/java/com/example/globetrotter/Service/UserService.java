

package com.example.globetrotter.Service;

import com.example.globetrotter.Model.User;
import com.example.globetrotter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(String username) {

        User user = new User();
        user.setUsername(username);
        user.setScore(0);
        user.generateInviteCode();

        return userRepository.save(user);
    }

    public boolean checkUserExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return true;
        } else {
            return false;
        }
    }


    public Map<String, String> getInviteDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String inviteCode = user.getInviteCode();
        String inviteLink = "https://globetrotter.com/challenge?inviteCode=" + inviteCode;
        String inviteImage = generateInviteImage(user.getUsername(), inviteCode, user.getScore());

        return Map.of(
                "inviteLink", inviteLink,
                "inviteImage", inviteImage
        );
    }


    private String generateInviteImage(String username, String inviteCode, int score) {
        String cloudinaryBase = "https://res.cloudinary.com/dougquo1c/image/upload";
        String inviteText = username + " scored " + score + "! Can you beat them?";


        String encodedText = URLEncoder.encode(inviteText, StandardCharsets.UTF_8)
                .replace("+", "%20");


        return cloudinaryBase + "/w_800,h_400,c_fill,l_text:Arial_40_bold:" + encodedText +
                ",co_white,g_south,y_50/v1741974018/invite_background.jpg";
    }

    public int getScoreByInviteCode(String inviteCode) {
        User user = userRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Invalid invite code"));
        return user.getScore();
    }
}
