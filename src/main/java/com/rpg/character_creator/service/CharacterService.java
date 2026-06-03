package com.rpg.character_creator.service;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.repository.CharacterRepository;
import org.springframework.stereotype.Service;
import com.rpg.character_creator.dto.CharacterRequestDTO;
import java.util.UUID;
import com.rpg.character_creator.exception.CharacterNotFoundException;
import com.rpg.character_creator.model.Race;
import com.rpg.character_creator.model.CharacterClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import com.rpg.character_creator.model.User;
import com.rpg.character_creator.repository.UserRepository;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository repository;

    private final UserRepository userRepository;

    private User getAuthenticatedUser() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow();
    }

    public CharacterService(
            CharacterRepository repository,
            UserRepository userRepository
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Character save(Character character) {
        return repository.save(character);
    }

    public Page<Character> findAll(Pageable pageable) {
        return repository.findAll(pageable);
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

        character.setUser(getAuthenticatedUser());

        return character;
    }
    public Character findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(CharacterNotFoundException::new);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Character update(UUID id, CharacterRequestDTO dto) {

        Character character = findById(id);

        character.setName(dto.name());
        character.setRace(dto.race());
        character.setCharacterClass(dto.characterClass());
        character.setStrength(dto.strength());
        character.setDexterity(dto.dexterity());
        character.setConstitution(dto.constitution());
        character.setIntelligence(dto.intelligence());
        character.setWisdom(dto.wisdom());
        character.setCharisma(dto.charisma());

        return repository.save(character);
    }

    public List<Character> findByRace(Race race) {
        return repository.findByRace(race);
    }

    public List<Character> findByCharacterClass(CharacterClass characterClass) {
        return repository.findByCharacterClass(characterClass);
    }

    public List<Character> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}