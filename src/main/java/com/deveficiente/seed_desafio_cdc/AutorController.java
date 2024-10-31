package com.deveficiente.seed_desafio_cdc;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AutorController {
    @PersistenceContext
    private EntityManager manager;

    @PostMapping(value = "/autores", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Transactional
    public ResponseEntity<String> adicionaAutor(@Valid Autor autor) {
        manager.persist(autor);
        return ResponseEntity.ok(autor.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public String handleEmailDuplicadoValidationExceptions(JdbcSQLIntegrityConstraintViolationException ex) {
        return "O e-mail informado já existe no banco de dados.";
    }
}
