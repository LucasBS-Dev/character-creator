package com.rpg.character_creator.service;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.repository.CharacterRepository;
import org.springframework.stereotype.Service;
import com.rpg.character_creator.dto.CharacterRequestDTO;

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

    public Character fromDTO(CharacterRequestDTO dto) {

        Character character = new Character();

        character.setName(dto.name());
        character.setRace(dto.race());
        character.setCharacterClass(dto.characterClass());
        character.setStrength(dto.strength());
        character.setDexterity(dto.dexterity());
        character.setConstitution(dto.constitution());
        character.setIntelligence(dto.intelligence());
        character.setWisdom(dto.wisdom());
        character.setCharisma(dto.charisma());

        return character;
    }
}