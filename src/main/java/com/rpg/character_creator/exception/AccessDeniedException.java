package com.rpg.character_creator.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("Acesso negado");
    }

}