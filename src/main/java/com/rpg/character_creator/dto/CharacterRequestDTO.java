package com.rpg.character_creator.dto;

import com.rpg.character_creator.model.CharacterClass;
import com.rpg.character_creator.model.Race;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CharacterRequestDTO(

        @NotBlank
        String name,

        @NotNull
        Race race,

        @NotNull
        CharacterClass characterClass,

        @Min(1)
        @Max(20)
        int strength,

        @Min(1)
        @Max(20)
        int dexterity,

        @Min(1)
        @Max(20)
        int constitution,

        @Min(1)
        @Max(20)
        int intelligence,

        @Min(1)
        @Max(20)
        int wisdom,

        @Min(1)
        @Max(20)
        int charisma

) {}