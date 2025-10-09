package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "participantes")
@PrimaryKeyJoinColumn(name = "id") // Usa Usuario.id como id primario
@Getter
@Setter
public class Participante extends Usuario {

    private String nombre;
    private String apellido;
    private String tipoDni;
    private int numeroDni;

    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscripcion> inscripciones = new ArrayList<>();
}
