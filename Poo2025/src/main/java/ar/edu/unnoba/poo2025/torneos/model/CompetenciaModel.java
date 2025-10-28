package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "competencias")
@Getter
@Setter
public class CompetenciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nombre;

    @Column(precision = 10, scale = 2, nullable=false)
    private BigDecimal precioBase;
 
    @Column(nullable=false)
    private Integer cupos;

    @ManyToOne
    @JoinColumn(name = "id_torneo", nullable = false)
    @JsonBackReference("torneo-competencia") 
    private TorneoModel torneo;
  
    @OneToMany(mappedBy = "competencia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("competencia-inscripcion") 
    private List<InscripcionModel> inscripciones = new ArrayList<>();
}
 