package ar.edu.unnoba.poo2025.torneos.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TorneoResponse2DTO {

    private long id;
    private String nombre;
    private Date fechaIni;
    private Date fechaFin;
    private boolean publicado;
}
