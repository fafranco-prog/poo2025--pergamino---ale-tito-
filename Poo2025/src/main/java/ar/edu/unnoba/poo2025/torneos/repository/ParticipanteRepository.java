package ar.edu.unnoba.poo2025.torneos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

public interface ParticipanteRepository extends JpaRepository<ParticipanteModel, Long> {
    //nada extra aun 
}
