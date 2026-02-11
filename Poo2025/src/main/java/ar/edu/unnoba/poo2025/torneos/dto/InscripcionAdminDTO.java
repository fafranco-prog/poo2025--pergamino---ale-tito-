package ar.edu.unnoba.poo2025.torneos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter

public class InscripcionAdminDTO {
private Long id;
    private LocalDate fecha;
    private BigDecimal precioPagado;
    private String nombreParticipante;
    private String apellidoParticipante;
    private String emailParticipante;    
}
