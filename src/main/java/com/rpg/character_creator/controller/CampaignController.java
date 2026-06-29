package com.rpg.character_creator.controller;

import com.rpg.character_creator.dto.CampaignResponseDTO;
import com.rpg.character_creator.model.Campaign;
import com.rpg.character_creator.service.CampaignService;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import com.rpg.character_creator.dto.CampaignDetailsResponseDTO;
import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService service;

    public CampaignController(
            CampaignService service
    ) {
        this.service = service;
    }

    @PostMapping
    public CampaignResponseDTO create(
            @RequestParam String name
    ) {

        return service.create(name);

    }

    @GetMapping
    public List<CampaignResponseDTO> findAll() {

        return service
                .findAll()
                .stream()
                .map(service::toDTO)
                .toList();

    }

    @GetMapping("/my")
    public List<CampaignResponseDTO> findMyCampaigns() {

        return service.findMyCampaigns();

    }

    @DeleteMapping(
            "/{campaignId}/characters/{characterId}"
    )
    public CampaignResponseDTO removeCharacter(

            @PathVariable
            UUID campaignId,

            @PathVariable
            UUID characterId

    ) {

        return service.removeCharacter(
                campaignId,
                characterId
        );

    }

    @PostMapping("/{campaignId}/characters/{characterId}")
    public CampaignResponseDTO addCharacter(

            @PathVariable UUID campaignId,

            @PathVariable UUID characterId

    ) {

        return service.addCharacter(
                campaignId,
                characterId
        );

    }

    @GetMapping("/{id}")
    public CampaignDetailsResponseDTO findDetailsById(

            @PathVariable
            UUID id

    ) {

        return service.findDetailsById(id);

    }

    @DeleteMapping("/{campaignId}/leave")
    public CampaignResponseDTO leaveCampaign(
            @PathVariable UUID campaignId
    ) {

        return service.leaveCampaign(campaignId);

    }

}