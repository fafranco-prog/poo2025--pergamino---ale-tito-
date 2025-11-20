package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "torneos")
@Getter
@Setter
public class TorneoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique=true, nullable=false)      
    private String nombre;
    @Column(nullable=false)
    private String descripcion;
    @Column(nullable=false)
    private Date fechaIni;

    private Date fechaFin;
    @Column(nullable=false)
    private Boolean publicado;
 
    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, orphanRemoval = true)  
    @JsonManagedReference("torneo-competencia")
    private List<CompetenciaModel> competencias = new ArrayList<>();
}
