package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaDetalleOutDTO;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;

@Service
public interface InscripcionService {

    public List<InscripcionModel> obtenerInscripciones();

    public InscripcionModel obtenerPorId(Long id);

    // public InscripcionModel crear(InscripcionModel inscripcion);
    // public void eliminar(Long id);
    // public void actualizar(Long id, InscripcionModel nuevosDatos);
    public CompetenciaDetalleOutDTO obtenerEstadisticasPorCompetencia(Long idCompetencia);

    public void registrarInscripcion(CompetenciaModel competencia, Long participanteId);

    public List<InscripcionModel> obtenerInscripcionesPorParticipante(Long participanteId);
    
}

