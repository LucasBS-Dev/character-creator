package com.rpg.character_creator.service;

import com.rpg.character_creator.dto.CampaignResponseDTO;
import com.rpg.character_creator.model.Campaign;
import com.rpg.character_creator.model.User;
import org.springframework.transaction.annotation.Transactional;
import com.rpg.character_creator.repository.CampaignRepository;
import com.rpg.character_creator.repository.InvitationRepository;
import com.rpg.character_creator.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import java.util.UUID;
import com.rpg.character_creator.dto.CampaignDetailsResponseDTO;
import com.rpg.character_creator.dto.PlayerResponseDTO;
import com.rpg.character_creator.dto.CharacterResponseDTO;
import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.repository.CharacterRepository;

@Service
public class CampaignService {

    private final CampaignRepository repository;

    private final UserRepository userRepository;

    private final CharacterRepository characterRepository;

    private final InvitationRepository invitationRepository;

    public CampaignService(
            CampaignRepository repository,
            UserRepository userRepository,
            CharacterRepository characterRepository,
            InvitationRepository invitationRepository
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
        this.invitationRepository = invitationRepository;
    }

    private User getAuthenticatedUser() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow();
    }

    public CampaignResponseDTO create(String name) {

        Campaign campaign = new Campaign();

        campaign.setName(name);

        campaign.setMaster(
                getAuthenticatedUser()
        );

        Campaign saved =
                repository.save(campaign);

        return toDTO(saved);

    }

    public List<Campaign> findAll() {

        return repository.findByMaster(
                getAuthenticatedUser()
        );

    }

    public CampaignDetailsResponseDTO findDetailsById(
            UUID id
    ) {

        Campaign campaign =

                repository
                        .findById(id)
                        .orElseThrow();

        if (
                !campaign
                        .getMaster()
                        .getId()
                        .equals(
                                getAuthenticatedUser()
                                        .getId()
                        )
        ) {

            throw new RuntimeException(
                    "Somente o mestre pode visualizar esta campanha"
            );

        }

        return toDetailsDTO(
                campaign
        );

    }

    public CampaignResponseDTO toDTO(Campaign campaign) {

        return new CampaignResponseDTO(

                campaign.getId(),

                campaign.getName(),

                campaign.getMaster().getUsername(),

                campaign
                        .getPlayers()
                        .stream()
                        .map(User::getUsername)
                        .toList(),

                campaign
                        .getCharacters()
                        .stream()
                        .map(Character::getName)
                        .toList()

        );

    }

    public CampaignResponseDTO addCharacter(
            UUID campaignId,
            UUID characterId
    ) {

        Campaign campaign =
                repository.findById(campaignId)
                        .orElseThrow();

        Character character =
                characterRepository.findById(characterId)
                        .orElseThrow();

        if (!campaign.getMaster().getId()
                .equals(getAuthenticatedUser().getId())) {

            throw new RuntimeException(
                    "Somente o mestre pode editar a campanha"
            );
        }

        campaign
                .getCharacters()
                .add(character);

        User owner =
                character.getUser();

        if (
                !campaign
                        .getPlayers()
                        .contains(owner)
        ) {

            campaign
                    .getPlayers()
                    .add(owner);

        }

        Campaign saved =
                repository.save(campaign);

        return toDTO(saved);

    }

    public List<CampaignResponseDTO> findMyCampaigns() {

        return repository
                .findByPlayersContains(
                        getAuthenticatedUser()
                )
                .stream()
                .map(this::toDTO)
                .toList();

    }
    public CampaignResponseDTO removeCharacter(
            UUID campaignId,
            UUID characterId
    ) {

        Campaign campaign =
                repository
                        .findById(campaignId)
                        .orElseThrow();

        if (
                !campaign
                        .getMaster()
                        .getId()
                        .equals(
                                getAuthenticatedUser()
                                        .getId()
                        )
        ) {

            throw new RuntimeException(
                    "Somente o mestre pode editar a campanha"
            );

        }

        campaign
                .getCharacters()
                .removeIf(

                        character ->

                                character
                                        .getId()
                                        .equals(
                                                characterId
                                        )

                );

        Campaign saved =
                repository.save(
                        campaign
                );

        return toDTO(saved);

    }

    public CampaignResponseDTO leaveCampaign(
            UUID campaignId
    ) {

        Campaign campaign =

                repository
                        .findById(campaignId)
                        .orElseThrow();

        User currentUser =
                getAuthenticatedUser();

        campaign
                .getPlayers()
                .removeIf(

                        player ->

                                player
                                        .getId()
                                        .equals(
                                                currentUser.getId()
                                        )

                );

        campaign
                .getCharacters()
                .removeIf(

                        character ->

                                character
                                        .getUser()
                                        .getId()
                                        .equals(
                                                currentUser.getId()
                                        )

                );

        Campaign saved =
                repository.save(
                        campaign
                );

        return toDTO(saved);

    }

    public CampaignDetailsResponseDTO toDetailsDTO(
            Campaign campaign
    ) {

        List<PlayerResponseDTO> players =

                campaign
                        .getPlayers()
                        .stream()
                        .map(player ->

                                new PlayerResponseDTO(

                                        player.getUsername(),

                                        campaign
                                                .getCharacters()
                                                .stream()
                                                .filter(character ->

                                                        character
                                                                .getUser()
                                                                .getId()
                                                                .equals(
                                                                        player.getId()
                                                                )

                                                )
                                                .map(CharacterResponseDTO::new)
                                                .toList()

                                )

                        )
                        .toList();

        return new CampaignDetailsResponseDTO(

                campaign.getId(),

                campaign.getName(),

                campaign.getMaster().getUsername(),

                players

        );

    }
    @Transactional
    public void delete(
            UUID id
    ) {

        Campaign campaign =

                repository
                        .findById(id)
                        .orElseThrow();

        if (!campaign.getMaster().getId()
                .equals(getAuthenticatedUser().getId())) {

            throw new RuntimeException(
                    "Somente o mestre pode excluir a campanha."
            );

        }

        invitationRepository.deleteByCampaign(campaign);

        repository.delete(campaign);

    }

}