package com.rpg.character_creator.repository;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.model.CharacterClass;
import com.rpg.character_creator.model.Race;
import com.rpg.character_creator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {

    List<Character> findByRace(Race race);

    List<Character> findByCharacterClass(CharacterClass characterClass);

    List<Character> findByNameContainingIgnoreCase(String name);

    List<Character> findByUser(User user);

    Page<Character> findByUser(User user, Pageable pageable);

    List<Character> findByUserAndRace(
            User user,
            Race race
    );

    List<Character> findByUserAndCharacterClass(
            User user,
            CharacterClass characterClass
    );

    List<Character> findByUserAndNameContainingIgnoreCase(
            User user,
            String name
    );

}