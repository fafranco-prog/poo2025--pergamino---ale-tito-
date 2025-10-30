package ar.edu.unnoba.poo2025.torneos.util;

import com.password4j.Password;

public class PasswordEncoder {

    public String codificar(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("El password no puede ser nulo.");
        }
        return Password.hash(rawPassword).withBcrypt().getResult();
    }

    public boolean verificar(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        return Password.check(rawPassword, encodedPassword).withBcrypt();
    }
}
