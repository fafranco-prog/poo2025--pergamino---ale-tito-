package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetenciaDetalleOutDTO {
    private int cantInscripciones;
    private BigDecimal total;
}
