package com.headout.globetrotter.controller;

import com.headout.globetrotter.dto.InvitationResponseDto;
import com.headout.globetrotter.dto.UserRegistrationDto;
import com.headout.globetrotter.model.User;
import com.headout.globetrotter.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    // Use an environment variable to define the frontend URL.
    // In production, this should be set to your actual frontend URL.
    @Value("${app.frontend.url}")
    private String frontendUrl;

    //User registration endpoint for "Challenge a Friend"
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setScoreCorrect(registrationDto.getScoreCorrect() != null ? registrationDto.getScoreCorrect() : 0);
        newUser.setScoreIncorrect(registrationDto.getScoreIncorrect() != null ? registrationDto.getScoreIncorrect() : 0);
        return ResponseEntity.ok(userService.registerUser(newUser));
    }

    // Endpoint to generate an invitation link along with a dynamic image URL.
    @GetMapping("/invite")
    public ResponseEntity<InvitationResponseDto> getInvitationLink(@RequestParam String username) {
        // Use the configured frontend URL instead of a hard-coded value.
        String inviteLink = frontendUrl + "/challenge?user=" + username;
        // Generate a dynamic image URL using a third-party service.
        // For example, dummyimage.com is used as a placeholder.
        String imageUrl = "https://dummyimage.com/600x400/000/fff.png&text=Join+" + username;
        InvitationResponseDto responseDto = new InvitationResponseDto(inviteLink, imageUrl);
        return ResponseEntity.ok(responseDto);
    }

    // Endpoint to retrieve user details by username (for showing scores on the invitation landing page).
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}