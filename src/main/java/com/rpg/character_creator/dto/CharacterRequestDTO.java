package com.rpg.character_creator.dto;

import com.rpg.character_creator.model.CharacterClass;
import com.rpg.character_creator.model.Race;

public record CharacterRequestDTO(
        String name,
        Race race,
        CharacterClass characterClass,
        int strength,
        int dexterity,
        int constitution,
        int intelligence,
        int wisdom,
        int charisma
) {}