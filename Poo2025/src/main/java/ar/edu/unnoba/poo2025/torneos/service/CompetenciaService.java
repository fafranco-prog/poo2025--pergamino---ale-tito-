package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;

import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaDetalleOutDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaPorTorneoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearCompetenciaDTO;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

public interface CompetenciaService {

    public List<CompetenciaModel> obtenerCompetencias();

    public CompetenciaModel obtenerPorId(Long id);

    public void actualizar(Long id, CrearCompetenciaDTO nuevosDatos, Long tournamentId);

    public void crearCompetenciaConTorneo(CrearCompetenciaDTO competencia, Long idTorneo);

    public void eliminar(Long id, Long idTorneo);

    public List<CompetenciaPorTorneoResponseDTO> obtenerCompetenciasDeTorneo(Long id);

    public CompetenciaDetalleOutDTO obtenerInscripcionesTotalesConMontos(Long id, Long idTorneo);

    public List<InscripcionModel> inscripcionesCompetencia(Long idCompetencia, Long idTorneo);

    public CompetenciaModel obtenerCompetenciaDeTorneo(TorneoModel torneo, Long competenciaId);

    public void validarInscripcion(CompetenciaModel competencia, Long participanteId);
}
