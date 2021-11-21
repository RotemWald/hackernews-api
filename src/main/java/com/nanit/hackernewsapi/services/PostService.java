package com.nanit.hackernewsapi.services;

import com.nanit.hackernewsapi.entities.Post;
import com.nanit.hackernewsapi.exceptions.EntityNotFoundException;
import com.nanit.hackernewsapi.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository repository;

    public Iterable<Post> getAll() {
        return repository.findAll();
    }

    public Iterable<Post> getAllSortedByMostVotes() {
        return repository.findAllByOrderByVotesDesc();
    }

    public Post create(Post post) {
        post.setVotes(0);
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
