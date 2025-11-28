package ar.edu.unnoba.poo2025.torneos.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
public class InscripcionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date fechaInscripcion;

    @Column(nullable = false)
    private BigDecimal precioPagado;
  
    @ManyToOne
    @JoinColumn(name = "id_participante", nullable = false)
    private ParticipanteModel participante;

    @ManyToOne
    @JoinColumn(name = "id_competencia", nullable = false)
    @JsonBackReference("competencia-inscripcion")
    private CompetenciaModel competencia;
}
