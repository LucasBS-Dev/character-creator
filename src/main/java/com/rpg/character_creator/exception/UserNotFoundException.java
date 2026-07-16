package com.rpg.character_creator.exception;

public class UserNotFoundException
        extends RuntimeException {

    public UserNotFoundException() {

        super("Usuário não encontrado.");

    }

}