package com.nanit.hackernewsapi.repositories;

import com.nanit.hackernewsapi.entities.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Transactional
    @Query("UPDATE Post SET votes = votes + 1 WHERE id = :id")
    @Modifying
    void vote(Integer id);

    Iterable<Post> findAllByOrderByVotesDesc();
}
