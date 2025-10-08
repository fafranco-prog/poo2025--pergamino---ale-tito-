package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInscripcion;

    private LocalDateTime fechaInscripcion;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioPagado;

    @ManyToOne
    @JoinColumn(name = "idParticipante", nullable = false)
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "idCompetencia", nullable = false)
    private Competencia competencia;
}
