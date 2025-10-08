package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "competencias")
@Getter
@Setter
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompetencia;

    private String nombre;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioBase;

    private Integer cupo;

    @ManyToOne
    @JoinColumn(name = "idTorneo", nullable = false)
    private Torneo torneo;

    @OneToMany(mappedBy = "competencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscripcion> inscripciones = new ArrayList<>();
}
