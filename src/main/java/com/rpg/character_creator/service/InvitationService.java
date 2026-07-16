package com.rpg.character_creator.service;

import com.rpg.character_creator.dto.InvitationResponseDTO;
import com.rpg.character_creator.exception.*;
import com.rpg.character_creator.model.Invitation;
import com.rpg.character_creator.model.User;
import com.rpg.character_creator.repository.InvitationRepository;
import com.rpg.character_creator.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.rpg.character_creator.model.Campaign;
import com.rpg.character_creator.repository.CampaignRepository;
import java.util.UUID;
import java.util.List;
import com.rpg.character_creator.repository.CharacterRepository;
import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.model.Campaign;

@Service
public class InvitationService {

    private final InvitationRepository repository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    private final CharacterRepository characterRepository;

    public InvitationService(

            InvitationRepository repository,

            UserRepository userRepository,

            CampaignRepository campaignRepository,

            CharacterRepository characterRepository

    ) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
        this.characterRepository = characterRepository;

    }

    private User getAuthenticatedUser() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        UserNotFoundException::new
                );

    }

    public InvitationResponseDTO toDTO(
            Invitation invitation
    ) {

        return new InvitationResponseDTO(

                invitation.getId(),

                invitation.getCampaign().getId(),

                invitation.getCampaign().getName(),

                invitation.getCampaign().getMaster().getUsername()

        );

    }

    public InvitationResponseDTO invitePlayer(

            UUID campaignId,

            String username

    ) {

        Campaign campaign =

                campaignRepository
                        .findById(campaignId)
                        .orElseThrow(
                                CampaignNotFoundException::new
                        );

        if (!campaign.getMaster().getId()
                .equals(getAuthenticatedUser().getId())) {

            throw new RuntimeException(
                    "Somente o mestre pode enviar convites"
            );

        }

        User player =

                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                UserNotFoundException::new
                        );

        if (
                repository.existsByCampaignAndPlayer(
                        campaign,
                        player
                )
        ) {

            throw new ConflictException(
                    "Este jogador já possui um convite para esta campanha."
            );

        }

        Invitation invitation = new Invitation();

        invitation.setCampaign(campaign);

        invitation.setPlayer(player);

        Invitation saved =
                repository.save(invitation);

        return toDTO(saved);

    }

    public List<InvitationResponseDTO> findMyInvitations() {

        return repository
                .findByPlayer(
                        getAuthenticatedUser()
                )
                .stream()
                .map(this::toDTO)
                .toList();

    }

    public void acceptInvitation(
            UUID invitationId,
            UUID characterId
    ) {

        Invitation invitation =

                repository
                        .findById(invitationId)
                        .orElseThrow(
                                InvitationNotFoundException::new
                        );

        User player =
                getAuthenticatedUser();

        if (!invitation.getPlayer().getId()
                .equals(player.getId())) {

            throw new RuntimeException(
                    "Este convite não pertence ao usuário."
            );

        }

        Character character =

                characterRepository
                        .findById(characterId)
                        .orElseThrow(
                                CharacterNotFoundException::new
                        );

        if (!character.getUser().getId()
                .equals(player.getId())) {

            throw new RuntimeException(
                    "O personagem não pertence ao jogador."
            );

        }

        Campaign campaign =
                invitation.getCampaign();

        if (!campaign.getPlayers().contains(player)) {

            campaign
                    .getPlayers()
                    .add(player);

        }

        campaign
                .getCharacters()
                .add(character);

        campaignRepository.save(campaign);

        repository.delete(invitation);

    }

}