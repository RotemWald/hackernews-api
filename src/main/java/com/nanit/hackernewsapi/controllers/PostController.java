package com.nanit.hackernewsapi.controllers;

import com.nanit.hackernewsapi.entities.Post;
import com.nanit.hackernewsapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/posts")
public class PostController {
    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<Iterable<Post>> getAllPosts() {
        Iterable<Post> posts = service.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
