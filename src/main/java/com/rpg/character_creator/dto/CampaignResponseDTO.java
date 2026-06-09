package com.rpg.character_creator.dto;

import java.util.UUID;

public record CampaignResponseDTO(

        UUID id,
        String name,
        String master

) {}