package com.koragpt.koragpt.controllers;

import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.models.dtos.auth.JwtResponseDTO;
import com.koragpt.koragpt.models.dtos.auth.LoginRequestDTO;
import com.koragpt.koragpt.repositories.UserRepository;
import com.koragpt.koragpt.security.JwtUtil;
import com.koragpt.koragpt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /*Use this to crosscheck username and password and we generate the JWT
     for the user and return it to be used to later APIs*/
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        Optional<User> userOpt = userRepository.findUserByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Use secure hash check
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = JwtUtil.generateToken(user.getUsername());
                JwtResponseDTO response = new JwtResponseDTO();
                response.setToken(token);
                response.setUsername(user.getUsername());
                response.setRole(user.getRole());
                response.setId(user.getId());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        Optional userOpt = userRepository.findUserByUsername(user.getUsername());
        System.out.println("User: " + user.getUsername() + " " + user.getPassword());
        if (userOpt.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.createUser(user);
        }
        return ResponseEntity.ok("Signup completed");
    }

}
