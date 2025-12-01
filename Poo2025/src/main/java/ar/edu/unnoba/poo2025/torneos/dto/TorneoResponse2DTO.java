package ar.edu.unnoba.poo2025.torneos.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TorneoResponse2DTO {

    private long id;
    private String nombre;
    private LocalDate fechaIni;
    private LocalDate fechaFin;
    private boolean publicado;
}
