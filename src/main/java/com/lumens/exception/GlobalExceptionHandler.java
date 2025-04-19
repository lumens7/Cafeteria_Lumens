package com.lumens.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicidadeException.class)
    public ResponseEntity<?> handleDuplicidadeException(DuplicidadeException ex) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("erro", "Duplicidade");
        resposta.put("mensagem", ex.getMessage());
        resposta.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(resposta, HttpStatus.CONFLICT);
    }


}