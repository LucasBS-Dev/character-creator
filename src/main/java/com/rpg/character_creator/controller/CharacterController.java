package com.rpg.character_creator.controller;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.service.CharacterService;
import org.springframework.web.bind.annotation.*;
import com.rpg.character_creator.dto.CharacterRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rpg.character_creator.model.Race;
import com.rpg.character_creator.model.CharacterClass;
import java.util.UUID;
import java.util.List;
import com.rpg.character_creator.dto.CharacterResponseDTO;

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
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterResponseDTO create(
            @Valid @RequestBody CharacterRequestDTO dto
    ) {

        Character character = service.fromDTO(dto);

        return new CharacterResponseDTO(
                service.save(character)
        );
    }

    @GetMapping
    public Page<CharacterResponseDTO> findAll(
            Pageable pageable
    ) {

        return service
                .findAll(pageable)
                .map(CharacterResponseDTO::new);

    }

    @GetMapping("/race/{race}")
    public List<Character> findByRace(@PathVariable Race race) {
        return service.findByRace(race);
    }

    @GetMapping("/class/{characterClass}")
    public List<Character> findByClass(@PathVariable CharacterClass characterClass) {
        return service.findByCharacterClass(characterClass);
    }

    @GetMapping("/search")
    public List<Character> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    @GetMapping("/{id}")
    public CharacterResponseDTO findById(
            @PathVariable UUID id
    ) {

        return new CharacterResponseDTO(
                service.findById(id)
        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public CharacterResponseDTO update(
            @PathVariable UUID id,
            @Valid @RequestBody CharacterRequestDTO dto
    ) {

        return new CharacterResponseDTO(
                service.update(id, dto)
        );
    }

}