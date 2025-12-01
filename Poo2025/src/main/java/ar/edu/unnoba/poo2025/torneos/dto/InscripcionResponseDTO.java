package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class InscripcionResponseDTO {

    Long id;
    LocalDate fecha;
    BigDecimal valor;
    Long torneoId;
    String torneoNombre;
    Long competenciaId;
    String competenciaNombre;
}
