package com.rpg.character_creator.controller;

import com.rpg.character_creator.dto.InvitationResponseDTO;
import com.rpg.character_creator.service.InvitationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invitations")
public class InvitationController {

    private final InvitationService service;

    public InvitationController(
            InvitationService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<InvitationResponseDTO> findMyInvitations() {

        return service.findMyInvitations();

    }

    @PostMapping("/{campaignId}")
    public InvitationResponseDTO invitePlayer(

            @PathVariable UUID campaignId,

            @RequestParam String username

    ) {

        return service.invitePlayer(
                campaignId,
                username
        );

    }

    @PostMapping("/{invitationId}/accept")
    public void acceptInvitation(

            @PathVariable
            UUID invitationId,

            @RequestParam
            UUID characterId

    ) {

        service.acceptInvitation(
                invitationId,
                characterId
        );

    }

}