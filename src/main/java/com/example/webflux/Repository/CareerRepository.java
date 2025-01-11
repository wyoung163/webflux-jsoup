package com.example.webflux.Repository;

import com.example.webflux.Domain.Career;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CareerRepository extends ReactiveCrudRepository<Career, Integer> {
}
