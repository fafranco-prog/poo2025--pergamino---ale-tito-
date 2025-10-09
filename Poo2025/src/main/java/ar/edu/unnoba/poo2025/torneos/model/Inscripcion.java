package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInscripcion;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioPagado;

    @ManyToOne
    @JoinColumn(name = "id_participante", nullable = false)
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "id_competencia", nullable = false)
    private Competencia competencia;
}
