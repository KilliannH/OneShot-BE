package com.killiann.oneshot.controllers;

import com.killiann.oneshot.entities.Pool;
import com.killiann.oneshot.exceptions.PoolNotFoundException;
import com.killiann.oneshot.repositories.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PoolController {

    @Autowired
    private PoolRepository poolRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping("/pools")
    List<Pool> all() {
        return poolRepository.findAll();
    }

    @PostMapping("/pools")
    Pool add(@RequestBody Pool newPool) {
        return poolRepository.save(newPool);
    }

    @PostMapping("/pools/userId/{userId}")
    public Pool addLikedUser(@RequestBody Pool newPool, @PathVariable String userId) {
        Optional<Pool> optUserPool = poolRepository.findByUserId(userId);

        Pool existingPool = optUserPool.get();

        // Get the likedUserId from newPool
        String likedUserId = newPool.getLiked().stream().findFirst().orElse(null);
        if (likedUserId == null) {
            throw new IllegalArgumentException("Liked user ID must not be null");
        }

        // Add likedUserId to existing pool's liked list if not already present
        if (!existingPool.getLiked().contains(likedUserId)) {
            existingPool.getLiked().add(likedUserId);
        }

        // Check if liked user exists and get their pool
        Optional<Pool> optLikedPool = poolRepository.findByUserId(likedUserId);
        if (optLikedPool.isPresent()) {
            Pool likedPool = optLikedPool.get();

            // Check if the liked user has also liked the current user (mutual match)
            if (likedPool.getLiked().contains(userId)) {
                // Add each other to the matches list
                existingPool.getMatches().add(likedUserId);
                likedPool.getMatches().add(userId);

                // Save both pools to reflect the mutual match
                poolRepository.save(likedPool);
            }
        }

        // Save the updated existing pool
        return poolRepository.save(existingPool);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/pools/{id}")
    Pool one(@PathVariable String id) {
        return poolRepository.findById(id)
                .orElseThrow(() -> new PoolNotFoundException(id));
    }

    @GetMapping("/pools/userId/{userId}")
    Pool byUserId(@PathVariable String userId) {
        return poolRepository.findByUserId(userId)
                .orElseThrow(() -> new PoolNotFoundException(userId));
    }

    @PutMapping("/pools/{id}")
    Pool replacePool(@RequestBody Pool newPool, @PathVariable String id) {

        return poolRepository.findById(id)
                .map(pool -> {
                    pool.setUserId(newPool.getUserId());
                    pool.setLiked(newPool.getLiked());
                    pool.setMatches(newPool.getMatches());
                    return poolRepository.save(pool);
                })
                .orElseThrow(() -> new PoolNotFoundException(id));
    }

    @DeleteMapping("/pools/{id}")
    void deletePool(@PathVariable String id) {
        poolRepository.deleteById(id);
    }
}
