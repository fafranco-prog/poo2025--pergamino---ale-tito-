package ar.edu.unnoba.poo2025.torneos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

public interface TorneoRepository extends JpaRepository<TorneoModel, Long> {
    // vacío 
}
