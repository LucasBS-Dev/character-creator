package com.rpg.character_creator.dto;

import java.util.UUID;
import com.rpg.character_creator.model.CharacterClass;
import com.rpg.character_creator.model.Race;

public record CharacterResponseDTO(
        UUID id,
        String name,
        Race race,
        CharacterClass characterClass,
        int level
) {}