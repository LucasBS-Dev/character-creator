package com.rpg.character_creator.repository;

import com.rpg.character_creator.model.Campaign;
import com.rpg.character_creator.model.Invitation;
import com.rpg.character_creator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvitationRepository
        extends JpaRepository<Invitation, UUID> {

    List<Invitation> findByPlayer(User player);

    void deleteByCampaign(Campaign campaign);

    boolean existsByCampaignAndPlayer(
            Campaign campaign,
            User player
    );
}