package com.rpg.character_creator.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(

        LocalDateTime timestamp,
        Integer status,
        String message

) {
}