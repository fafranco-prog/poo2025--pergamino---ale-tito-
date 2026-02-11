package ar.edu.unnoba.poo2025.torneos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;

public interface InscripcionRepository extends JpaRepository<InscripcionModel, Long> {

    List<InscripcionModel> findByParticipanteId(Long participanteId);
 
    
    @Query("SELECT i FROM InscripcionModel i WHERE i.competencia.id = :idCompetencia AND i.competencia.torneo.id = :idTorneo")
    List<InscripcionModel> buscarPorCompetenciaYTorneo(@Param("idCompetencia") Long idCompetencia, @Param("idTorneo") Long idTorneo);    
}
