package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TorneoDetalleDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private boolean publicado;
    private int totalInscripciones;
    private BigDecimal montoTotal;
    private LocalDate fechaFin;
    private LocalDate fechaIni;
}
