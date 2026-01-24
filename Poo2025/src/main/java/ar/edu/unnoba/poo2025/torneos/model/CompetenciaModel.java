package ar.edu.unnoba.poo2025.torneos.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "competencias")
@Getter
@Setter
public class CompetenciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique=true) 
    private String nombre;

    @Column(nullable = false)
    private BigDecimal precioBase;
 
    @Column(nullable = false)
    private Integer cupos;

    @Transient
    private int cuposDisponibles;

    @ManyToOne
    @JoinColumn(name = "id_torneo", nullable = false)
    @JsonBackReference("torneo-competencia")
    private TorneoModel torneo;

    @OneToMany(mappedBy = "competencia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("competencia-inscripcion")
    private List<InscripcionModel> inscripciones = new ArrayList<>();
}
