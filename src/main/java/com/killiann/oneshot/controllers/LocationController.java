package com.killiann.oneshot.controllers;

import com.killiann.oneshot.entities.Location;
import com.killiann.oneshot.exceptions.LocationNotFoundException;
import com.killiann.oneshot.exceptions.ProfileNotFoundException;
import com.killiann.oneshot.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping("/locations")
    List<Location> all() {
        return locationRepository.findAll();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/locations")
    Location add(@RequestBody Location newLocation) {
        return locationRepository.save(newLocation);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/locations/userId/{userId}")
    Location add(@RequestBody Location newLocation, @PathVariable String userId) {
        Optional<Location> optUserLocation = locationRepository.findByUserId(userId);

        // first time, let's create location
        if(optUserLocation.isEmpty()) {
            return locationRepository.save(newLocation);
        }
        // not first time, update it
        return optUserLocation
                .map(location -> {
                    location.setUserId(newLocation.getUserId());
                    location.setLat(newLocation.getLat());
                    location.setLng(newLocation.getLng());
                    return locationRepository.save(location);
                })
                .orElseThrow(() -> new LocationNotFoundException(userId));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/locations/{id}")
    Location one(@PathVariable String id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    @GetMapping("/locations/userId/{userId}")
    Location byUserId(@PathVariable String userId) {
        return locationRepository.findByUserId(userId)
                .orElseThrow(() -> new LocationNotFoundException(userId));
    }

    @PutMapping("/locations/{id}")
    Location replaceLocation(@RequestBody Location newLocation, @PathVariable String id) {

        return locationRepository.findById(id)
                .map(location -> {
                    location.setUserId(newLocation.getUserId());
                    location.setLat(newLocation.getLat());
                    location.setLng(newLocation.getLng());
                    return locationRepository.save(location);
                })
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    @DeleteMapping("/locations/{id}")
    void deleteLocation(@PathVariable String id) {
        locationRepository.deleteById(id);
    }
}
