package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter 
public class CrearCompetenciaDTO {

    private String nombre;
    private Integer cupos;
    private BigDecimal precio;
}
