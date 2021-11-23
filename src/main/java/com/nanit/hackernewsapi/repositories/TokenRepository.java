package com.nanit.hackernewsapi.repositories;

import com.nanit.hackernewsapi.entities.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {

}
