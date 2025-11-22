package ar.edu.unnoba.poo2025.torneos.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearTorneoDTO {

    private String nombre;
    private String descripcion;
    private Date fechaFin;
    private Date fechaIni;
}
