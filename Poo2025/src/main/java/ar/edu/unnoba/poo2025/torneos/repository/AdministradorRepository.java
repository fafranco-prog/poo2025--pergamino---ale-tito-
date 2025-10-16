package ar.edu.unnoba.poo2025.torneos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;

@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorModel, Long> {
    // nada extra aun
}

