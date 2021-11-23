package com.nanit.hackernewsapi.services;

import com.nanit.hackernewsapi.entities.Post;
import com.nanit.hackernewsapi.entities.Token;
import com.nanit.hackernewsapi.entities.redis.Posts;
import com.nanit.hackernewsapi.exceptions.EntityNotFoundException;
import com.nanit.hackernewsapi.repositories.PostRepository;
import com.nanit.hackernewsapi.repositories.redis.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class PostService {
    @Autowired
    private PostRepository dbRepository;

    @Autowired
    private PostsRepository cacheRepository;

    public Iterable<Post> getAll() {
        return dbRepository.findAll();
    }

    public Iterable<Post> getAllSortedByVotes() {
        return dbRepository.findAllByOrderByVotesDesc();
    }

    public Iterable<Post> getAllSortedByScore() {
        Optional<Posts> cachedPosts = cacheRepository.findById("all");
        if (cachedPosts.isPresent()) {
            Posts posts = cachedPosts.get();
            long ageInMinutes = ChronoUnit.MINUTES.between(posts.getLastUpdated(), LocalDateTime.now());
            if (ageInMinutes >= 1) {
                CompletableFuture.runAsync(() -> {
                    Iterable<Post> dbPosts = dbRepository.findAllByOrderByScoreDesc();
                    posts.setPosts(dbPosts);
                    posts.setLastUpdated(LocalDateTime.now());
                    cacheRepository.save(posts);
                });
            }
            return cachedPosts.get().getPosts();
        } else {
            Iterable<Post> dbPosts = dbRepository.findAllByOrderByScoreDesc();
            Posts postsToCache = new Posts("all", dbPosts, LocalDateTime.now());
            cacheRepository.save(postsToCache);
            return dbPosts;
        }
    }

    public Post create(Post post, Token token) {
        post.setVotes(0);
        post.setScore(0.0);
        post.setUserId(token.getUserId());
        return dbRepository.save(post);
    }

    public Post vote(Integer id) {
        dbRepository.vote(id);
        Post post = this.getById(id);
        return post;
    }

    private Post getById(Integer id) {
        Optional<Post> post = dbRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new EntityNotFoundException("entity not found");
        }
    }
}
