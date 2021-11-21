package com.nanit.hackernewsapi.services;

import com.nanit.hackernewsapi.entities.Token;
import com.nanit.hackernewsapi.entities.User;
import com.nanit.hackernewsapi.exceptions.EntityNotFoundException;
import com.nanit.hackernewsapi.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    private TokenRepository repository;

    public Token create(User user) {
        Token token = new Token();
        token.setUserId(user.getId());
        repository.save(token);
        return token;
    }

    public Token getById(String id) {
        Optional<Token> token = repository.findById(id);
        if (token.isPresent()) {
            return token.get();
        } else {
            throw new EntityNotFoundException("invalid token");
        }
    }
}
