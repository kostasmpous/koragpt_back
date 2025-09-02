package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        //validate that user with the same username does not exist
        Optional<User> existingUser = this.userRepository.findUserByUsername(user.getUsername());
        if (existingUser != null) {
            //throw new BootcampException(HttpStatus.BAD_REQUEST, "User with username " + user.getUsername() + " already exists.");
        }
        user = this.userRepository.save(user);
        return user;
    }
}
