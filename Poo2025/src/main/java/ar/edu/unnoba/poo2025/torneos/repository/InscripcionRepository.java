package ar.edu.unnoba.poo2025.torneos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;

public interface InscripcionRepository extends JpaRepository<InscripcionModel, Long> {

    List<InscripcionModel> findByParticipanteId(Long participanteId);
    
}
