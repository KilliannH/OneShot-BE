package com.killiann.oneshot.repositories;

import com.killiann.oneshot.entities.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Optional<Profile> findByUserId(String userId);
}
