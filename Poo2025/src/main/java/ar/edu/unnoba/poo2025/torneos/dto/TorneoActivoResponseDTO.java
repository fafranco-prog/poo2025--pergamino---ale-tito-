package ar.edu.unnoba.poo2025.torneos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TorneoActivoResponseDTO {

    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;

}
