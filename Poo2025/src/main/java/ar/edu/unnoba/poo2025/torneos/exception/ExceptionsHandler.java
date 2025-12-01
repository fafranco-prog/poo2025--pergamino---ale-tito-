package ar.edu.unnoba.poo2025.torneos.exception;

import java.util.NoSuchElementException;
import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({NoSuchElementException.class, ResourceNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Recurso no encontrado: " + e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateResourceException(DuplicateResourceException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Conflicto de recurso: " + e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Credenciales inválidas: " + e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleJwtAuthenticationException(JwtAuthenticationException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Error de autenticación JWT: " + e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SelfDeletionException.class)
    public ResponseEntity<Map<String, String>> handleSelfDeletionException(SelfDeletionException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Acción prohibida: " + e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Map<String, String>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Solicitud inválida: Falta la cabecera de autorización"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleNotAllowedException(NotAllowedException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Acción no permitida: " + e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(Collections.singletonMap("error", "Solicitud inválida: " + e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
