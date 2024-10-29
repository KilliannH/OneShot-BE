package com.killiann.oneshot.controllers;

import com.killiann.oneshot.controllers.dto.MatchDto;
import com.killiann.oneshot.entities.Pool;
import com.killiann.oneshot.exceptions.PoolNotFoundException;
import com.killiann.oneshot.repositories.MessageRepository;
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

    @Autowired
    private MessageRepository messageRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping("/pools")
    List<Pool> all() {
        return poolRepository.findAll();
    }

    @PostMapping("/pools")
    Pool add(@RequestBody Pool newPool) {
        return poolRepository.save(newPool);
    }

    @PostMapping("/pools/liked/userId/{userId}")
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

    @PostMapping("/pools/disliked/userId/{userId}")
    public Pool addDislikedUser(@RequestBody Pool newPool, @PathVariable String userId) {
        Optional<Pool> optUserPool = poolRepository.findByUserId(userId);

        Pool existingPool = optUserPool.get();

        // Get the dislikedUserId from newPool
        String dislikedUserId = newPool.getDisliked().stream().findFirst().orElse(null);
        if (dislikedUserId == null) {
            throw new IllegalArgumentException("Disliked user ID must not be null");
        }

        // Add likedUserId to existing pool's liked list if not already present
        if (!existingPool.getDisliked().contains(dislikedUserId)) {
            existingPool.getDisliked().add(dislikedUserId);
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

    @PostMapping("/pools/matches")
    void deleteMatch(@RequestBody MatchDto matchDto) {
        Pool pool1 = poolRepository.findByUserId(matchDto.getUserId1()).orElseThrow(() -> new PoolNotFoundException(matchDto.getUserId1()));
        Pool pool2 = poolRepository.findByUserId(matchDto.getUserId2()).orElseThrow(() -> new PoolNotFoundException(matchDto.getUserId2()));

        // Remove match with userId2 from pool1's matches list
        pool1.getMatches().removeIf(matchId -> matchId.equals(matchDto.getUserId2()));

        // Remove match with userId1 from pool2's matches list
        pool2.getMatches().removeIf(matchId -> matchId.equals(matchDto.getUserId1()));

        // Construct possible roomIds
        String roomId1 = matchDto.getUserId1() + "_" + matchDto.getUserId2();
        String roomId2 = matchDto.getUserId2() + "_" + matchDto.getUserId1();

        // Delete all messages for either roomId1 or roomId2
        messageRepository.deleteByRoomId(roomId1);
        messageRepository.deleteByRoomId(roomId2);

        // Save both pools back to the repository
        poolRepository.save(pool1);
        poolRepository.save(pool2);
    }

    @DeleteMapping("/pools/{id}")
    void deletePool(@PathVariable String id) {
        poolRepository.deleteById(id);
    }
}
