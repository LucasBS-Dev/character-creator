package com.rpg.character_creator.service;

import com.rpg.character_creator.dto.CampaignResponseDTO;
import com.rpg.character_creator.model.Campaign;
import com.rpg.character_creator.model.User;

import com.rpg.character_creator.repository.CampaignRepository;
import com.rpg.character_creator.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository repository;

    private final UserRepository userRepository;

    public CampaignService(
            CampaignRepository repository,
            UserRepository userRepository
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
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
                campaign.getMaster().getUsername()
        );

    }

}