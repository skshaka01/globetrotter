package com.headout.globetrotter.service;

import com.headout.globetrotter.exception.DuplicateUsernameException;
import com.headout.globetrotter.model.User;
import com.headout.globetrotter.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUsernameException("Username already exists. Please choose unique username.");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateScore(String username, boolean correct) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (correct) {
                user.setScoreCorrect(user.getScoreCorrect() + 1);
            } else {
                user.setScoreIncorrect(user.getScoreIncorrect() + 1);
            }
            return userRepository.save(user);
        }
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}