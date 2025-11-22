package ar.edu.unnoba.poo2025.torneos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TorneoDetalleDTO {

    private Long id;
    private String nombre;
    private boolean publicado;
    private int totalInscripciones;
    private double montoTotal;
}
