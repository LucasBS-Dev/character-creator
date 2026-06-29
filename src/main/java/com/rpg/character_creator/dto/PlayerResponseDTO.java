package com.rpg.character_creator.dto;

import java.util.List;

public record PlayerResponseDTO(

        String username,

        List<CharacterResponseDTO> characters

) {}