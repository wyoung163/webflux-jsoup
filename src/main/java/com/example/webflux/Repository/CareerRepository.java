package com.example.webflux.Repository;

import com.example.webflux.Domain.Career;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CareerRepository extends R2dbcRepository<Career, Integer> {
}
