package ar.edu.unnoba.poo2025.torneos.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaDetalleOutDTO;
import ar.edu.unnoba.poo2025.torneos.exception.ResourceNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.repository.InscripcionRepository;
import ar.edu.unnoba.poo2025.torneos.repository.ParticipanteRepository;

@Service
public class InscripcionServiceImp implements InscripcionService {

    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Override
    public List<InscripcionModel> obtenerInscripciones() {
        return inscripcionRepository.findAll();
    }
    
    @Override
    public List<InscripcionModel> obtenerPorCompetenciaYTorneo(Long idCompetencia, Long idTorneo) {
    return inscripcionRepository.buscarPorCompetenciaYTorneo(idCompetencia, idTorneo);
    }


    @Override
    public InscripcionModel obtenerPorId(Long id) {
        return inscripcionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró una inscripcion con el ID: " + id));
    }

    // @Override
    // public void eliminar(Long id) {
    //     inscripcionRepository.deleteById(id);
    // }
    @Override
    public CompetenciaDetalleOutDTO obtenerEstadisticasPorCompetencia(Long idCompetencia) {

        List<InscripcionModel> inscripciones = obtenerInscripciones();

        List<InscripcionModel> filtradas = inscripciones.stream()
                .filter(i -> i.getCompetencia().getId().equals(idCompetencia))
                .toList();

        int totalInscriptos = filtradas.size();

        BigDecimal montoTotal = filtradas.stream()
                .map(InscripcionModel::getPrecioPagado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CompetenciaDetalleOutDTO dto = new CompetenciaDetalleOutDTO();
        dto.setCantInscripciones(totalInscriptos);
        dto.setTotal(montoTotal);

        return dto;
    }

    // @Override
    // public InscripcionModel crear(InscripcionModel inscripcion) {
    //     ParticipanteModel participante = participanteRepository.findById(inscripcion.getParticipante().getId()).orElse(null);
    //     CompetenciaModel competencia = competenciaRepository.findById(inscripcion.getCompetencia().getId()).orElse(null);
    //     inscripcion.setParticipante(participante);
    //     inscripcion.setCompetencia(competencia);
    //     return inscripcionRepository.save(inscripcion);
    // }
    // @Override
    // public void actualizar(Long id, InscripcionModel nuevosDatos) {
    //     InscripcionModel existente = inscripcionRepository.findById(id).orElse(null);
    //     if (existente != null) {
    //         existente.setParticipante(nuevosDatos.getParticipante());
    //         existente.setCompetencia(nuevosDatos.getCompetencia());
    //         existente.setPrecioPagado(nuevosDatos.getPrecioPagado());
    //         existente.setFechaInscripcion(nuevosDatos.getFechaInscripcion());
    //         inscripcionRepository.save(existente);
    //     }
    // }
    @Override
    public void registrarInscripcion(CompetenciaModel competencia, Long participanteId) {
        ParticipanteModel participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un participante con el ID: " + participanteId));

        Long torneoId = competencia.getTorneo().getId();

        BigDecimal precioFinal = competencia.getPrecioBase();

        boolean yaInscriptoEnTorneo = inscripcionRepository.findAll().stream()
                .filter(inscripcion -> inscripcion.getParticipante().getId().equals(participanteId))
                .anyMatch(inscripcion -> inscripcion.getCompetencia().getTorneo().getId().equals(torneoId) && !inscripcion.getCompetencia().getId().equals(competencia.getId()));

        if (yaInscriptoEnTorneo) {
            precioFinal = precioFinal.divide(new BigDecimal("2.0"));
        }

        InscripcionModel nuevaInscripcion = new InscripcionModel();
        nuevaInscripcion.setParticipante(participante);
        nuevaInscripcion.setCompetencia(competencia);
        nuevaInscripcion.setFechaInscripcion(LocalDate.now());
        nuevaInscripcion.setPrecioPagado(precioFinal);

        inscripcionRepository.save(nuevaInscripcion);

    }
    
    @Override
    public List<InscripcionModel> obtenerInscripcionesPorParticipante(Long participanteId) {
        return inscripcionRepository.findByParticipanteId(participanteId);
    }
}
