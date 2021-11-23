package com.nanit.hackernewsapi.repositories.redis;

import com.nanit.hackernewsapi.entities.redis.Posts;
import org.springframework.data.repository.CrudRepository;

public interface PostsRepository extends CrudRepository<Posts, String> {

}
