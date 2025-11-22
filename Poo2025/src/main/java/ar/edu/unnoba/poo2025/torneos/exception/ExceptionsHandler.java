package ar.edu.unnoba.poo2025.torneos.exception;

import java.util.NoSuchElementException;
import java.util.Collections;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({NoSuchElementException.class, ResourceNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDuplicateResourceException(DuplicateResourceException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<?> handleJwtAuthenticationException(JwtAuthenticationException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SelfDeletionException.class)
    public ResponseEntity<?> handleSelfDeletionException(SelfDeletionException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Falta la cabecera de autorización"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
