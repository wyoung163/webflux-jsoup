package com.example.webflux.Repository;

import com.example.webflux.Domain.Career;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CareerRepository extends ReactiveCrudRepository<Career, Integer> {
    Flux<Career> saveAll(String[] companies, String[] categories, String[] teams, String[] titles, String[] contents, String[] links, String[] experiences, String[] conditions, String[] requirements, String[] preferences, String[] durations);
}
