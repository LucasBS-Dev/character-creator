package com.rpg.character_creator.controller;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @GetMapping("/test")
    public String test() {
        return "API funcionando";
    }

    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @PostMapping
    public Character create(@RequestBody Character character) {
        return service.save(character);
    }

    @GetMapping
    public List<Character> findAll() {
        return service.findAll();
    }
}