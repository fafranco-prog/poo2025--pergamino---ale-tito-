package ar.edu.unnoba.poo2025.torneos.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class CrearAdminRequestDTO {

    @NotBlank(message= "El Email es obligatorio")
    private String email;
    @NotBlank(message = "La Constraseña es obligatoria")
    private String password;
}
