package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class InscripcionDetalleDTO {

    Long id;
    LocalDate fecha;
    BigDecimal valor;
    Long competenciaId;
    String competenciaNombre;
    Long torneoId;
    String torneoNombre;
    String torneoDescripcion;
    LocalDate torneoFechaInicio;
    LocalDate torneoFechaFin;
}
