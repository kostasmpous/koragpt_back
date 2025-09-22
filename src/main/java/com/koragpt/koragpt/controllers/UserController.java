package com.koragpt.koragpt.controllers;
import com.koragpt.koragpt.models.Contact;
import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.models.dtos.auth.ChangePasswordDTO;
import com.koragpt.koragpt.models.dtos.auth.SignupRequestDTO;
import com.koragpt.koragpt.models.dtos.user.UserDTO;
import com.koragpt.koragpt.repositories.ContactRepository;
import com.koragpt.koragpt.repositories.UserRepository;
import com.koragpt.koragpt.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository, ContactRepository contactRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody SignupRequestDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(Authentication auth) {
        User u = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<Contact> billTo = contactRepository.findAllByUserAndType(u, "BillTo");
        Contact c = billTo.isEmpty() ? null : billTo.get(0);
        return ResponseEntity.ok(UserDTO.from(u, c));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateMe(@RequestBody UserDTO body, Authentication auth) {
        User u = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Update core user fields (null-safe)
        if (body.getEmail() != null)      u.setEmail(body.getEmail());
        if (body.getFirstName() != null)  u.setFirst_name(body.getFirstName());
        if (body.getLastName() != null)   u.setLast_name(body.getLastName());
        userRepository.save(u);

        // Upsert BillTo contact properly
        List<Contact> list = contactRepository.findAllByUserAndType(u, "BillTo");
        Contact c = list.isEmpty() ? null : list.get(0);
        if (c == null) {
            c = new Contact();
            c.setUser(u);
            c.setType("BillTo");
        }
        if (body.getAddress() != null)     c.setAddress(body.getAddress());
        if (body.getCity() != null)        c.setCity(body.getCity());
        if (body.getCountry() != null)     c.setCountry(body.getCountry());
        if (body.getPostalCode() != null)  c.setPostalcode(body.getPostalCode());
        if (body.getFirstName() != null)   c.setFirstName(body.getFirstName());
        if (body.getLastName() != null)   c.setLastName(body.getLastName());

        contactRepository.save(c);

        return ResponseEntity.ok(UserDTO.from(u, c));
    }

    @PostMapping("/me/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO body, Authentication auth) {
        if (body == null || body.getOldPassword() == null || body.getNewPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing passwords");
        }

        User u = userRepository.findUserByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(body.getOldPassword(), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        u.setPassword(passwordEncoder.encode(body.getNewPassword()));
        userRepository.save(u);
        return ResponseEntity.ok().build();
    }

}
