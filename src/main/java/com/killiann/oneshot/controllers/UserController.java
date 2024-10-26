package com.killiann.oneshot.controllers;

import com.killiann.oneshot.entities.User;
import com.killiann.oneshot.exceptions.UserNotFoundException;
import com.killiann.oneshot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users/{id}")
    User one(@PathVariable String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable String id) {

        return userRepository.findById(id)
                .map(userModel -> {
                    userModel.setUsername(newUser.getUsername());
                    userModel.setPassword(newUser.getPassword());
                    userModel.setEmail(newUser.getEmail());
                    return userRepository.save(userModel);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }
}