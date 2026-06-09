package com.rpg.character_creator.repository;

import com.rpg.character_creator.model.Campaign;
import com.rpg.character_creator.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CampaignRepository
        extends JpaRepository<Campaign, UUID> {

    List<Campaign> findByMaster(User master);

}