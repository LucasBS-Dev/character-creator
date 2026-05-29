package com.rpg.character_creator.exception;

public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException() {
        super("Character not found");
    }
}