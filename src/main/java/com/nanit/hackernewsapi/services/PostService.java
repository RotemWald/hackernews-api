package com.nanit.hackernewsapi.services;

import com.nanit.hackernewsapi.entities.Post;
import com.nanit.hackernewsapi.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository repository;

    public Iterable<Post> getAll() {
        return repository.findAll();
    }
}
