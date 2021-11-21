package com.nanit.hackernewsapi.services;

import com.nanit.hackernewsapi.dto.LoginRequest;
import com.nanit.hackernewsapi.dto.LoginResponse;
import com.nanit.hackernewsapi.entities.Token;
import com.nanit.hackernewsapi.entities.User;
import com.nanit.hackernewsapi.exceptions.EntityNotFoundException;
import com.nanit.hackernewsapi.repositories.TokenRepository;
import com.nanit.hackernewsapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public User create(User user) {
        user.setPassword(user.getPassword() + "1234");
        User savedUser = userRepository.save(user);
        savedUser.setPassword("");
        return savedUser;
    }

    public LoginResponse login(LoginRequest request) {
        User user = getByUsername(request.getUsername());
        String encryptedPassword = request.getPassword() + "1234";
        if (user.getPassword().equals(encryptedPassword)) {
            Token token = new Token();
            token.setUserId(user.getId());
            tokenRepository.save(token);
            return new LoginResponse(token.getToken());
        } else {
            throw new RuntimeException("Unauthorized");
        }
    }

    private User getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("user not found");
        }
    }


}
