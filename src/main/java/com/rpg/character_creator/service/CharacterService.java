package com.rpg.character_creator.service;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CharacterService {

    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public Character create(Character character) {
        return repository.save(character);
    }

    public List<Character> findAll() {
        return repository.findAll();
    }

    public Character findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found"));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}