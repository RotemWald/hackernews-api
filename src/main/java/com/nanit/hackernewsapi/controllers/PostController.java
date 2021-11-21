package com.nanit.hackernewsapi.controllers;

import com.nanit.hackernewsapi.entities.Post;
import com.nanit.hackernewsapi.enums.Ordering;
import com.nanit.hackernewsapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/posts")
public class PostController {
    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<Iterable<Post>> getAll(@RequestParam(name = "orderBy", required = false, defaultValue = "DEFAULT") Ordering order) {
        Iterable posts;
        if (order == Ordering.BY_VOTES) {
            posts = service.getAllSortedByMostVotes();
        } else {
            posts = service.getAll();
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return new ResponseEntity<>(service.create(post), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/votes")
    public ResponseEntity<Post> vote(@PathVariable Integer id) {
        return new ResponseEntity<>(service.vote(id), HttpStatus.OK);
    }
}
