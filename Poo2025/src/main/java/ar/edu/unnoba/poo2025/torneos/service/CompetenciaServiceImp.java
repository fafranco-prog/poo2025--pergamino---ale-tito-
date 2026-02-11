package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaDetalleOutDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaPorTorneoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearCompetenciaDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscripcionAdminDTO;
import ar.edu.unnoba.poo2025.torneos.exception.DuplicateResourceException;
import ar.edu.unnoba.poo2025.torneos.exception.NotAllowedException;
import ar.edu.unnoba.poo2025.torneos.exception.ResourceNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.CompetenciaRepository;

@Service
public class CompetenciaServiceImp implements CompetenciaService {

    @Autowired
    private TorneoService torneoService;
    @Autowired
    private InscripcionService inscripcionService;
    @Autowired
    private CompetenciaRepository competenciaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CompetenciaModel> obtenerCompetencias() {
        return competenciaRepository.findAll();
    }

    @Override
    public CompetenciaModel obtenerPorId(Long id) {
        CompetenciaModel competencia = competenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró competencia"));

        Integer disponibles = competenciaRepository.consultarCuposDisponibles(id);
        competencia.setCuposDisponibles(disponibles != null ? disponibles : 0);

        return competencia;
    }

    @Override
    public List<CompetenciaPorTorneoResponseDTO> obtenerCompetenciasDeTorneo(Long id) {
        torneoService.obtenerPorId(id);
        return obtenerCompetencias().stream()
                .filter(c -> c.getTorneo().getId().equals(id))
                .map(c -> modelMapper.map(c, CompetenciaPorTorneoResponseDTO.class))
                .toList();
    }

    @Override
    public CompetenciaDetalleOutDTO obtenerInscripcionesTotalesConMontos(Long id, Long idTorneo) {
        CompetenciaModel comp = obtenerPorId(id);
        torneoService.obtenerPorId(idTorneo);
        if (!comp.getTorneo().getId().equals(idTorneo)) {
            throw new ResourceNotFoundException("La competencia con ID: " + id + " no pertence al torneo con ID:" + idTorneo);
        }
        return inscripcionService.obtenerEstadisticasPorCompetencia(id);
    }

    @Override
    public void crearCompetenciaConTorneo(CrearCompetenciaDTO competencia, Long idTorneo) {
        TorneoModel torneo = torneoService.obtenerPorId(idTorneo);
        torneoService.validarTorneoNoPublicado(torneo);
        //SE PODRIA SEPARAR LA VALIDACION DE EXISTENCIA
        if (competenciaRepository.buscarPorNombre(competencia.getNombre()).isPresent()) {
            throw new DuplicateResourceException("Ya existe una competencia con el nombre '" + competencia.getNombre() + "'.");
        }
        CompetenciaModel compe = modelMapper.map(competencia, CompetenciaModel.class);
        compe.setTorneo(torneo);
        competenciaRepository.save(compe);
    }

    @Override
    public void actualizar(Long id, CrearCompetenciaDTO nuevosDatos, Long tournamentId) {
        TorneoModel torneo = torneoService.obtenerPorId(tournamentId);
        torneoService.validarTorneoNoPublicado(torneo);
        CompetenciaModel existente = obtenerPorId(id);
        if (!existente.getTorneo().getId().equals(tournamentId)) {
            throw new NotAllowedException("La competencia no pertenece al torneo especificado.");
        }
        //NO TOMA EN CUENTA CAMPOS NULOS/INEXISTENTES
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(nuevosDatos, existente);
        competenciaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long idTorneo) {
        TorneoModel torneo = torneoService.obtenerPorId(idTorneo);
        torneoService.validarTorneoNoPublicado(torneo);
        CompetenciaModel compe = obtenerPorId(id);
        if (!torneo.getId().equals(compe.getTorneo().getId())) {
            throw new ResourceNotFoundException("El torneo no es el asignado a la competencia");
        }
        competenciaRepository.delete(compe);
    }

    /*@Override
    public List<InscripcionModel> inscripcionesCompetencia(Long idCompetencia, Long idTorneo) {
        TorneoModel torneo = torneoService.obtenerPorId(idTorneo);
        CompetenciaModel compe = obtenerPorId(idCompetencia);

        if (!torneo.getId().equals(compe.getTorneo().getId())) {
            throw new ResourceNotFoundException("El ID del torneo no coincide con el torneo de la competencia.");
        }

        return inscripcionService.obtenerInscripciones()
                .stream()
                .filter(ins -> ins.getCompetencia().getId().equals(idCompetencia))
                .toList();
    }*/
    @Override
    public List<InscripcionAdminDTO> inscripcionesCompetencia(Long idCompetencia, Long idTorneo) {
        CompetenciaModel compe = obtenerPorId(idCompetencia);
        if (!compe.getTorneo().getId().equals(idTorneo)) {
            throw new ResourceNotFoundException("La competencia no pertenece al torneo indicado.");
        }

        List<InscripcionModel> inscripciones = inscripcionService.obtenerPorCompetenciaYTorneo(idCompetencia, idTorneo);

        return inscripciones.stream()
            .map(ins -> modelMapper.map(ins, InscripcionAdminDTO.class))
            .toList();
        }

    @Override
    public CompetenciaModel obtenerCompetenciaDeTorneo(TorneoModel torneo, Long competenciaId) {
        return torneo.getCompetencias().stream()
                .filter(c -> c.getId().equals(competenciaId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("La competencia " + competenciaId + " no forma parte del torneo " + torneo.getId()));
    }

    @Override
    public void validarInscripcion(CompetenciaModel competencia, Long participanteId) {

        competencia.getInscripciones().stream()
                .filter(i -> i.getParticipante().getId().equals(participanteId))
                .findFirst().ifPresent(i -> {
                    throw new DuplicateResourceException("El participante ya se encuentra inscripto en esta competencia.");
                });

        if (competencia.getInscripciones().size() >= competencia.getCupos()) {
            throw new NotAllowedException("No hay cupo disponible en esta competencia.");
        }
    }
}
