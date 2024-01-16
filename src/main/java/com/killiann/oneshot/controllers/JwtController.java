package com.killiann.oneshot.controllers;

import com.killiann.oneshot.entities.User;
import com.killiann.oneshot.errors.GenericError;
import com.killiann.oneshot.jwt.UserDetailsImpl;
import com.killiann.oneshot.payloads.LoginRequest;
import com.killiann.oneshot.payloads.LoginResponse;
import com.killiann.oneshot.payloads.SignupRequest;
import com.killiann.oneshot.payloads.SignupResponse;
import com.killiann.oneshot.repositories.UserRepository;
import com.killiann.oneshot.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class JwtController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        User connUser = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Bad credentials"));

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(connUser.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtil.generateJwtToken(userDetails);

        return ResponseEntity.ok()
                .body(new LoginResponse(jwtToken, userDetails.getId()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        // by default username is substring of @ (left part) - xxxxxx@b.fr
        // points are deleted

        String username = signUpRequest.getEmail().split("@")[0];
        username = username.replaceAll("[^a-z]+", "");

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new GenericError("/signup", "Bad Request", "Username is already in use", 400));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new GenericError("/signup", "Bad Request", "Email is already in use", 400));
        }

        // Create new user
        User user = new User(username,
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        User dbUser = userRepository.save(user);

        // log user in
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dbUser.getUsername(), signUpRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtil.generateJwtToken(userDetails);

        return ResponseEntity.status(201)
                .body(new SignupResponse(jwtToken, userDetails.getId()));
    }
}