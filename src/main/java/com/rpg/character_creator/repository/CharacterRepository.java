package com.rpg.character_creator.repository;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.model.CharacterClass;
import com.rpg.character_creator.model.Race;
import com.rpg.character_creator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {

    List<Character> findByRace(Race race);

    List<Character> findByCharacterClass(CharacterClass characterClass);

    List<Character> findByNameContainingIgnoreCase(String name);

    List<Character> findByUser(User user);

}