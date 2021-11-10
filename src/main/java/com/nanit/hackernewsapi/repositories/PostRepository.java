package com.nanit.hackernewsapi.repositories;

import com.nanit.hackernewsapi.entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {

}
