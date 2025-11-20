package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
public class InscripcionModel {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Date fechaInscripcion;

    @Column(nullable=false)  
    private double precioPagado;

    @ManyToOne
    @JoinColumn(name = "id_participante", nullable = false)
    private ParticipanteModel participante;

    @ManyToOne
    @JoinColumn(name = "id_competencia", nullable = false)
    @JsonBackReference("competencia-inscripcion")
    private CompetenciaModel competencia;
}
