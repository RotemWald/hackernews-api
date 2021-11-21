package com.nanit.hackernewsapi.controllers;

import com.nanit.hackernewsapi.entities.Post;
import com.nanit.hackernewsapi.entities.Token;
import com.nanit.hackernewsapi.enums.Ordering;
import com.nanit.hackernewsapi.services.PostService;
import com.nanit.hackernewsapi.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<Iterable<Post>> getAll(@RequestParam(name = "orderBy", required = false, defaultValue = "DEFAULT") Ordering order) {
        Iterable posts;
        if (order == Ordering.BY_VOTES) {
            posts = postService.getAllSortedByMostVotes();
        } else {
            posts = postService.getAll();
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestHeader("user-token") String tokenId, @RequestBody Post post) {
        Token token = tokenService.getById(tokenId);
        return new ResponseEntity<>(postService.create(post, token), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/votes")
    public ResponseEntity<Post> vote(@PathVariable Integer id) {
        return new ResponseEntity<>(postService.vote(id), HttpStatus.OK);
    }
}
