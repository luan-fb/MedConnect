package com.medconecct.medconecct.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import com.medconecct.medconecct.exception.ConflitoHorarioException; // Importe a nova exceção

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConflitoHorarioException.class)
    public ResponseEntity<Map<String, String>> handleConflitoHorario(ConflitoHorarioException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
