package com.nanit.hackernewsapi.repositories;

import com.nanit.hackernewsapi.entities.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Transactional
    @Query(value = "UPDATE `post` SET `votes` = `votes` + 1, `score` = (`votes` + 1) * (UNIX_TIMESTAMP(`created_at`) * 0.0000000001) WHERE id = :id", nativeQuery = true)
    @Modifying
    void vote(@Param(value = "id") Integer id);

    Iterable<Post> findAllByOrderByVotesDesc();

    Iterable<Post> findAllByOrderByScoreDesc();
}
