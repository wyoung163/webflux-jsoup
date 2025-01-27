package com.example.webflux.Repository;

import com.example.webflux.Domain.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    @Query("SELECT email FROM user")
    public Flux<String> findAllEmails();
}
