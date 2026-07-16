package com.rpg.character_creator.exception;

public class CampaignNotFoundException
        extends RuntimeException {

    public CampaignNotFoundException() {

        super("Campanha não encontrada.");

    }

}