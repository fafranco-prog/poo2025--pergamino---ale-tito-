package ar.edu.unnoba.poo2025.torneos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ParticipanteRequestDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;

    public void validate() {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida.");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es requerido.");
        }
        if (documentType == null || documentType.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento es requerido.");
        }
        if (documentNumber == null || documentNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento es requerido.");
        }
    }
}
