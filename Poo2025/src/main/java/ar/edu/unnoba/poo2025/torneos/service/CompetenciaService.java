package ar.edu.unnoba.poo2025.torneos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.CompetenciaRepository;
import ar.edu.unnoba.poo2025.torneos.repository.TorneoRepository;

@Service
public class CompetenciaService {
     
    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private CompetenciaRepository competenciaRepository;

    public List<CompetenciaModel> obtenerCompetencias() {
        return competenciaRepository.findAll();
    }

    public Optional<CompetenciaModel> obtenerPorId(Long id) {
        return competenciaRepository.findById(id);
    }

    public CompetenciaModel crear(CompetenciaModel competencia) {
        TorneoModel torneo = torneoRepository.findById(competencia.getTorneo().getId()).orElse(null);
        competencia.setTorneo(torneo);
        return competenciaRepository.save(competencia);
    }

    public void eliminar(Long id) {
        competenciaRepository.deleteById(id);
    }

    public void actualizar(Long id, CompetenciaModel nuevosDatos) {
        CompetenciaModel existente = competenciaRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(nuevosDatos.getNombre());
            existente.setCupos(nuevosDatos.getCupos());
            existente.setPrecioBase(nuevosDatos.getPrecioBase());
            existente.setTorneo(nuevosDatos.getTorneo());
            
            TorneoModel torneo = torneoRepository.findById(nuevosDatos.getTorneo().getId()).orElse(null);
            existente.setTorneo(torneo);
            competenciaRepository.save(existente);
        }
    } 
}
