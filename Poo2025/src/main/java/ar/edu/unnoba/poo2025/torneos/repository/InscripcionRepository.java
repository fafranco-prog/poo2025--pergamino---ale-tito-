package ar.edu.unnoba.poo2025.torneos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;

public interface InscripcionRepository extends JpaRepository<InscripcionModel, Long> {
    // vacío por ahora
}
