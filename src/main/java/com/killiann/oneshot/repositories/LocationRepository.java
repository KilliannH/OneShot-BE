package com.killiann.oneshot.repositories;

import com.killiann.oneshot.entities.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LocationRepository extends MongoRepository<Location, String> {
    Optional<Location> findByUserId(String userId);
}
