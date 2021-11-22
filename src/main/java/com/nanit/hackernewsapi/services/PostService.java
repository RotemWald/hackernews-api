package com.nanit.hackernewsapi.services;

import com.nanit.hackernewsapi.entities.Post;
import com.nanit.hackernewsapi.entities.Token;
import com.nanit.hackernewsapi.exceptions.EntityNotFoundException;
import com.nanit.hackernewsapi.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class PostService {
    @Autowired
    private PostRepository repository;

    public Iterable<Post> getAll() {
        return repository.findAll();
    }

    public Iterable<Post> getAllSortedByVotes() {
        return repository.findAllByOrderByVotesDesc();
    }

    public Iterable<Post> getAllSortedByScore() {
        return repository.findAllByOrderByScoreDesc();
    }

    public Post create(Post post, Token token) {
        post.setVotes(0);
        post.setScore(0.0);
        post.setUserId(token.getUserId());
        return repository.save(post);
    }

    public Post vote(Integer id) {
        repository.vote(id);
        Post post = this.getById(id);
        return post;
    }

    private Post getById(Integer id) {
        Optional<Post> post = repository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new EntityNotFoundException("entity not found");
        }
    }
}
