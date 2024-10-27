package com.killiann.oneshot.repositories;

import com.killiann.oneshot.entities.Location;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends MongoRepository<Location, String> {
    Optional<Location> findByUserId(String userId);
    List<Location> findByLocationNear(Point point, Distance distance);
}
