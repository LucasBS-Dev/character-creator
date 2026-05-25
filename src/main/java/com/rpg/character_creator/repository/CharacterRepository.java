package com.rpg.character_creator.repository;

import com.rpg.character_creator.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {
}