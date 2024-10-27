package com.killiann.oneshot.controllers;

import com.killiann.oneshot.controllers.dto.LocationDto;
import com.killiann.oneshot.entities.Location;
import com.killiann.oneshot.exceptions.LocationNotFoundException;
import com.killiann.oneshot.exceptions.ProfileNotFoundException;
import com.killiann.oneshot.repositories.LocationRepository;
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
    Location add(@RequestBody LocationDto newLocationDto) {
        Location newLocation = convertLocationDto(newLocationDto);
        return locationRepository.save(newLocation);
    }

    @PostMapping("/locations/userId/{userId}")
    Location add(@RequestBody LocationDto newLocationDto, @PathVariable String userId) {
        Optional<Location> optUserLocation = locationRepository.findByUserId(userId);

        // first time, let's create location
        if(optUserLocation.isEmpty()) {
            Location newLocation = convertLocationDto(newLocationDto);
            return locationRepository.save(newLocation);
        }
        // not first time, update it
        Location newLocation = convertLocationDto(newLocationDto);
        return optUserLocation
                .map(location -> {
                    location.setUserId(newLocation.getUserId());
                    location.setLocation(newLocation.getLocation().getX(), newLocation.getLocation().getY());
                    return locationRepository.save(location);
                })
                .orElseThrow(() -> new LocationNotFoundException(userId));
    }

    @PostMapping("/locations/near")
    List<Location> findNearbyLocations(@RequestBody LocationDto locationDto) {
        Point point = new Point(Double.parseDouble(locationDto.getLng()), Double.parseDouble(locationDto.getLat()));
        Distance distance = new Distance(locationDto.getRadius(), Metrics.KILOMETERS);
        return locationRepository.findByLocationNear(point, distance);
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
    Location replaceLocation(@RequestBody LocationDto newLocationDto, @PathVariable String id) {

        Location newLocation = convertLocationDto(newLocationDto);
        return locationRepository.findById(id)
                .map(location -> {
                    location.setUserId(newLocation.getUserId());
                    location.setLocation(newLocation.getLocation().getX(), newLocation.getLocation().getY());
                    return locationRepository.save(location);
                })
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    @DeleteMapping("/locations/{id}")
    void deleteLocation(@PathVariable String id) {
        locationRepository.deleteById(id);
    }

    private Location convertLocationDto(LocationDto locationDto) {
        Location location = new Location();
        location.setUserId(locationDto.getUserId());
        location.setLocation(Double.parseDouble(locationDto.getLng()), Double.parseDouble(locationDto.getLat()));
        return location;
    }
}
