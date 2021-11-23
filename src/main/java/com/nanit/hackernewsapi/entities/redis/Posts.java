package com.nanit.hackernewsapi.entities.redis;

import com.nanit.hackernewsapi.entities.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RedisHash(value = "Posts", timeToLive = 1800)
@Getter
@Setter
@NoArgsConstructor
public class Posts {
    @Id
    private String id;

    private List<Post> posts;

    private LocalDateTime lastUpdated;

    public Posts(String id, Iterable<Post> posts, LocalDateTime lastUpdated) {
        this.id = id;
        this.posts = new ArrayList<>();
        this.lastUpdated = lastUpdated;

        posts.forEach(post -> this.posts.add(post));
    }

    public void setPosts(Iterable<Post> posts) {
        this.posts.clear();
        posts.forEach(post -> this.posts.add(post));
    }
}
