package ar.edu.unnoba.poo2025.torneos.util;

import com.password4j.Password;

import ar.edu.unnoba.poo2025.torneos.exception.InvalidCredentialsException;

public class PasswordEncoder {

    public String codificar(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new InvalidCredentialsException("El password no puede ser nulo.");
        }
        return Password.hash(rawPassword).withBcrypt().getResult();
    }

    public boolean verificar(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            throw new InvalidCredentialsException("Las contraseñas proporcionadas no pueden ser nulas.");
        }
        return Password.check(rawPassword, encodedPassword).withBcrypt();
    }
}
