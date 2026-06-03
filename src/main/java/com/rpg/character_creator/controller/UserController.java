package com.rpg.character_creator.controller;

import com.rpg.character_creator.model.User;
import com.rpg.character_creator.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(
            @RequestBody User user
    ) {
        return service.create(user);
    }

    @GetMapping("/public")
    public String publicRoute() {
        return "funcionando";
    }

}