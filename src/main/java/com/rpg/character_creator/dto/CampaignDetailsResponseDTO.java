package com.rpg.character_creator.dto;

import java.util.List;
import java.util.UUID;

public record CampaignDetailsResponseDTO(

        UUID id,

        String name,

        String master,

        List<PlayerResponseDTO> players

) {}