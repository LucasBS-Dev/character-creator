package com.rpg.character_creator.service;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public Character save(Character character) {
        return repository.save(character);
    }

    public List<Character> findAll() {
        return repository.findAll();
    }
}