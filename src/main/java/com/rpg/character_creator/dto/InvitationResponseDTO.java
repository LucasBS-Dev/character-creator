package com.rpg.character_creator.dto;

import java.util.UUID;

public record InvitationResponseDTO(

        UUID id,

        UUID campaignId,

        String campaign,

        String master

) {}