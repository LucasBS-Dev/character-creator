package com.rpg.character_creator.service;

import com.rpg.character_creator.dto.CampaignResponseDTO;
import com.rpg.character_creator.model.Campaign;
import com.rpg.character_creator.model.User;

import com.rpg.character_creator.repository.CampaignRepository;
import com.rpg.character_creator.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import java.util.UUID;

import com.rpg.character_creator.model.Character;
import com.rpg.character_creator.repository.CharacterRepository;

@Service
public class CampaignService {

    private final CampaignRepository repository;

    private final UserRepository userRepository;

    private final CharacterRepository characterRepository;

    public CampaignService(
            CampaignRepository repository,
            UserRepository userRepository,
            CharacterRepository characterRepository
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
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

}