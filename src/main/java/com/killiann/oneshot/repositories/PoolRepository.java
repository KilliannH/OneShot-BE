package com.killiann.oneshot.repositories;

import com.killiann.oneshot.entities.Pool;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PoolRepository extends MongoRepository<Pool, String> {
    Optional<Pool> findByUserId(String userId);
}
