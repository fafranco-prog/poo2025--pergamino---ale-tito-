package ar.edu.unnoba.poo2025.torneos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.repository.InscripcionRepository;

import java.util.List;
import java.util.Optional;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.repository.CompetenciaRepository;
import ar.edu.unnoba.poo2025.torneos.repository.ParticipanteRepository;

@Service
public class InscripcionService {
    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private CompetenciaRepository competenciaRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<InscripcionModel> obtenerInscripciones() {
        return inscripcionRepository.findAll();
    }

    public Optional<InscripcionModel> obtenerPorId(Long id) {
        return inscripcionRepository.findById(id);
    }

    public InscripcionModel crear(InscripcionModel inscripcion) {
        ParticipanteModel participante = participanteRepository.findById(inscripcion.getParticipante().getId()).orElse(null); 
        CompetenciaModel competencia = competenciaRepository.findById(inscripcion.getCompetencia().getId()).orElse(null);
        inscripcion.setParticipante(participante);
        inscripcion.setCompetencia(competencia);
        return inscripcionRepository.save(inscripcion);
    }

    public void eliminar(Long id) {
        inscripcionRepository.deleteById(id);
    }

    public void actualizar(Long id, InscripcionModel nuevosDatos) {
        InscripcionModel existente = inscripcionRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setParticipante(nuevosDatos.getParticipante());
            existente.setCompetencia(nuevosDatos.getCompetencia());
            existente.setPrecioPagado(nuevosDatos.getPrecioPagado());
            existente.setFechaInscripcion(nuevosDatos.getFechaInscripcion());
            inscripcionRepository.save(existente);
        }
    }
} 