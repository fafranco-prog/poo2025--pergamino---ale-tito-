package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Table(name = "participante")
@DiscriminatorValue("PARTICIPANTE")
@Setter
@Getter
public class ParticipanteModel extends UsuarioModel {

    @Column(nullable = true)
    private String nombre;
    @Column(nullable = true)
    private String apellido;
    @Column(nullable = true)
    private String tipoDni;
    @Column(nullable = true)
    private Integer numeroDni;

}
