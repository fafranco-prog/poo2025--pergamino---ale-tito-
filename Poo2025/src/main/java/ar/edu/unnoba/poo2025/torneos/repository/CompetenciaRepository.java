package ar.edu.unnoba.poo2025.torneos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;

public interface CompetenciaRepository extends JpaRepository<CompetenciaModel, Long> {
    // nada extra por ahora
} 
