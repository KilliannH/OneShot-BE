package com.killiann.oneshot.controllers;

import com.killiann.oneshot.entities.Pool;
import com.killiann.oneshot.exceptions.PoolNotFoundException;
import com.killiann.oneshot.repositories.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @Secured("ROLE_ADMIN")
    @PostMapping("/pools")
    Pool add(@RequestBody Pool newPool) {
        return poolRepository.save(newPool);
    }

    @PostMapping("/pools/userId/{userId}")
    Pool add(@RequestBody Pool newPool, @PathVariable String userId) {
        Optional<Pool> optUserPool = poolRepository.findByUserId(userId);

        // first time, let's create pool
        if(optUserPool.isEmpty()) {
            return poolRepository.save(newPool);
        }
        // not first time, update it
        return optUserPool
                .map(pool -> {
                    pool.setUserId(newPool.getUserId());
                    pool.setLiked(newPool.getLiked());
                    pool.setMatches(newPool.getMatches());
                    return poolRepository.save(pool);
                })
                .orElseThrow(() -> new PoolNotFoundException(userId));
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
