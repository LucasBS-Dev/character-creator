package com.rpg.character_creator.dto;

import com.rpg.character_creator.model.Character;

import java.util.UUID;

public record CharacterResponseDTO(

        UUID id,
        String name,
        String race,
        String characterClass,
        Integer level,
        Integer strength,
        Integer dexterity,
        Integer constitution,
        Integer intelligence,
        Integer wisdom,
        Integer charisma,
        String owner

) {

    public CharacterResponseDTO(Character character) {

        this(
                character.getId(),
                character.getName(),
                character.getRace().name(),
                character.getCharacterClass().name(),
                character.getLevel(),
                character.getStrength(),
                character.getDexterity(),
                character.getConstitution(),
                character.getIntelligence(),
                character.getWisdom(),
                character.getCharisma(),
                character.getUser().getUsername()
        );
    }

}