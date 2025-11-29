package ar.edu.unnoba.poo2025.torneos.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaDetalleOutDTO;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.repository.CompetenciaRepository;
import ar.edu.unnoba.poo2025.torneos.repository.InscripcionRepository;
import ar.edu.unnoba.poo2025.torneos.repository.ParticipanteRepository;
@Service
public class InscripcionServiceImp implements  InscripcionService{
    //    @Autowired
    //private ParticipanteRepository participanteRepository;
    //@Autowired
    //private CompetenciaRepository competenciaRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;
    @Override
    public List<InscripcionModel> obtenerInscripciones() {
        return inscripcionRepository.findAll();
    }
    @Override
    public Optional<InscripcionModel> obtenerPorId(Long id) {
        return inscripcionRepository.findById(id);
    }
    public void eliminar(Long id) {
        inscripcionRepository.deleteById(id);
    }
    /* 
    public InscripcionModel crear(InscripcionModel inscripcion) {
        ParticipanteModel participante = participanteRepository.findById(inscripcion.getParticipante().getId()).orElse(null);
        CompetenciaModel competencia = competenciaRepository.findById(inscripcion.getCompetencia().getId()).orElse(null);
        inscripcion.setParticipante(participante);
        inscripcion.setCompetencia(competencia);
        return inscripcionRepository.save(inscripcion);
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
    }*/
    
    
    //TP6
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

    

}

