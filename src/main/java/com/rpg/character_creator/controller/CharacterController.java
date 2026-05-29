package com.rpg.character_creator.controller;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.service.CharacterService;
import org.springframework.web.bind.annotation.*;
import com.rpg.character_creator.dto.CharacterRequestDTO;
import java.util.UUID;

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
    public Character create(@RequestBody CharacterRequestDTO dto) {

        Character character = service.fromDTO(dto);

        return service.save(character);
    }

    @GetMapping
    public List<Character> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Character findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }
    @PutMapping("/{id}")
    public Character update(@PathVariable UUID id,
                            @RequestBody CharacterRequestDTO dto) {

        return service.update(id, dto);
    }


}