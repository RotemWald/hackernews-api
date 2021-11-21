package com.nanit.hackernewsapi.services;

import com.nanit.hackernewsapi.dto.LoginRequest;
import com.nanit.hackernewsapi.dto.LoginResponse;
import com.nanit.hackernewsapi.entities.Token;
import com.nanit.hackernewsapi.entities.User;
import com.nanit.hackernewsapi.exceptions.EntityNotFoundException;
import com.nanit.hackernewsapi.exceptions.UnauthorizedException;
import com.nanit.hackernewsapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        savedUser.setPassword("");
        return savedUser;
    }

    public LoginResponse login(LoginRequest request) {
        User user = getByUsername(request.getUsername());
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            Token token = tokenService.create(user);
            return new LoginResponse(token.getToken());
        } else {
            throw new UnauthorizedException("bad credentials");
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
