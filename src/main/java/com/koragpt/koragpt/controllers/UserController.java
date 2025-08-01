package com.koragpt.koragpt.controllers;

import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService UserService;

    @Autowired
    public UserController(UserService UserService){
        this.UserService = UserService;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) throws Exception {
        return this.UserService.createUser(user);
    }
}
