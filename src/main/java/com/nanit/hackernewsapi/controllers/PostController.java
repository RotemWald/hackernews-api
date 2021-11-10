package com.nanit.hackernewsapi.controllers;

import com.nanit.hackernewsapi.entities.Post;
import com.nanit.hackernewsapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/posts")
public class PostController {
    @Autowired
    private PostService service;

    @GetMapping
    public Iterable<Post> getAllPosts() {
        return service.getAllPosts();
    }
}
