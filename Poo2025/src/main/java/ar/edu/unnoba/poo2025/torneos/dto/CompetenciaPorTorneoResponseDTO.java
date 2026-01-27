package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetenciaPorTorneoResponseDTO {
    
    private Long id;   
    private String nombre;  
    private BigDecimal precioBase; 
    private Integer cupos; 
    private Long torneo;
}
