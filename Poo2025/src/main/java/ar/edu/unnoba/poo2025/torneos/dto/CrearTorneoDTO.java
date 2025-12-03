package ar.edu.unnoba.poo2025.torneos.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearTorneoDTO {

    @NotBlank(message="El nombre no puede ser nulo")
    private String nombre;
    @NotBlank(message="La descripcion no puede ser nula")
    private String descripcion;
    private LocalDate fechaFin;
    /*tener en cuenta que el FutureOrPresent toma como fecha de comparacion
    la fecha de la maquina en donde se este ejecutando la pc por lo tanto 
    en caso de desfazaje con el horario esto va a afectar esta validacion */
    @NotNull(message="La fecha de inicio no puede ser nula")
    @FutureOrPresent(message="La fecha de inicio debe ser hoy o una fecha posterior")
    private LocalDate fechaIni;
}
