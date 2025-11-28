package ar.edu.unnoba.poo2025.torneos.dto;

import java.time.LocalDate;
 
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActualizarTorneoDTO {
    private String nombre;
    private String descripcion;
    private LocalDate fechaIni;
    private LocalDate fechaFin;
}
