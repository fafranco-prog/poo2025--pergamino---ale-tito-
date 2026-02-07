package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;

import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscripcionResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

public interface ParticipanteService {

    // List<ParticipanteModel> obtenerParticipantes();
    ParticipanteModel obtenerPorId(Long id);

    public void crear(ParticipanteModel participant);

    public ParticipanteModel obtenerPorEmail(String email);

    // void eliminar(Long id);
    // void actualizar(Long id, ParticipanteModel nuevosDatos);
    String authenticate(ParticipanteModel participante);

    ParticipanteModel authorization(String token);

    List<TorneoModel> getTorneosActivos();

    TorneoModel getTorneoById(Long id);

    List<CompetenciaResponseDTO> getCompetenciaByTorneoId(Long tournamentId);

    CompetenciaModel getCompetenciaById(Long tournamentId, Long id);

    void inscribirseEnCompetencia(Long tournamentId, Long competenciaId, Long participanteId);

    //List<InscripcionResponseDTO> getInscripciones();

    InscripcionModel getInscripcionById(ParticipanteModel participante, Long id);

    
    public List<InscripcionResponseDTO> getInscripcionesByParticipante(Long participanteId);
    
}
