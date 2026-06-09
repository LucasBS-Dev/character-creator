package com.rpg.character_creator.exception;

import com.rpg.character_creator.dto.ErrorResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(
            AccessDeniedException ex
    ) {

        ErrorResponseDTO response =
                new ErrorResponseDTO(
                        LocalDateTime.now(),
                        HttpStatus.FORBIDDEN.value(),
                        ex.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCharacterNotFound(
            CharacterNotFoundException ex
    ) {

        ErrorResponseDTO response =
                new ErrorResponseDTO(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Personagem não encontrado"
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            org.springframework.web.bind.MethodArgumentNotValidException.class
    )
    public ResponseEntity<ErrorResponseDTO> handleValidation(
            org.springframework.web.bind.MethodArgumentNotValidException ex
    ) {

        String message =
                ex.getBindingResult()
                        .getFieldErrors()
                        .get(0)
                        .getField()
                        + ": "
                        +
                        ex.getBindingResult()
                                .getFieldErrors()
                                .get(0)
                                .getDefaultMessage();

        ErrorResponseDTO response =
                new ErrorResponseDTO(
                        java.time.LocalDateTime.now(),
                        400,
                        message
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

}