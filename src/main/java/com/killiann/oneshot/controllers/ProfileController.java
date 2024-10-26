package com.killiann.oneshot.controllers;

import com.killiann.oneshot.entities.Profile;
import com.killiann.oneshot.entities.User;
import com.killiann.oneshot.exceptions.ProfileNotFoundException;
import com.killiann.oneshot.exceptions.UserNotFoundException;
import com.killiann.oneshot.repositories.ProfileRepository;
import com.killiann.oneshot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/profiles")
    List<Profile> all() {
        return profileRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/profiles")
    Profile add(@RequestBody Profile newProfile) {
        return profileRepository.save(newProfile);
    }

    @GetMapping("/profiles/{id}")
    Profile one(@PathVariable String id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }

    @GetMapping("/profiles/userId/{userId}")
    Profile byUserId(@PathVariable String userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(userId));
    }

    @PutMapping("/profiles/{id}")
    Profile replaceProfile(@RequestBody Profile newProfile, @PathVariable String id) {

        return profileRepository.findById(id)
                .map(profile -> {
                    profile.setDisplayName(newProfile.getDisplayName());
                    profile.setGender(newProfile.getGender());
                    profile.setUserId(newProfile.getUserId());
                    profile.setBirthday(newProfile.getBirthday());
                    profile.setJob(newProfile.getJob());
                    profile.setBio(newProfile.getBio());
                    profile.setImageUrls(newProfile.getImageUrls());
                    return profileRepository.save(profile);
                })
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/profiles/{id}")
    void deleteProfile(@PathVariable String id) {
        profileRepository.deleteById(id);
    }
}