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
        Iterable<Post> rawPosts = repository.findAll();
        List<Post> posts = new ArrayList<>();

        for (Post post : rawPosts) {
            long age = ChronoUnit.HOURS.between(post.getCreatedAt(), LocalDateTime.now());
            post.setScore((post.getVotes() - 1) / Math.pow(age + 2, 1.8));
            posts.add(post);
        }

        Collections.sort(posts, (p1, p2) -> {
            if (p2.getScore() > p1.getScore())
                return 1;
            if (p1.getScore() > p2.getScore())
                return -1;
            return 0;
        });

        return posts;
    }

    public Post create(Post post, Token token) {
        post.setVotes(0);
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
