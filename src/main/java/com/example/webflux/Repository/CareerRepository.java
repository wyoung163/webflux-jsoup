package com.example.webflux.Repository;

import com.example.webflux.Domain.Career;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CareerRepository extends R2dbcRepository<Career, Integer> {
//public interface CareerRepository extends ReactiveCrudRepository<Career, Integer> {
    @Query("SELECT * FROM career WHERE created_at = CURDATE()")
    public Flux<Career> findCurDateCareers();
}
