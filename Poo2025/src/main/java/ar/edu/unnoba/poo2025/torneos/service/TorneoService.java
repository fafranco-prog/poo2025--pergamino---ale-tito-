package ar.edu.unnoba.poo2025.torneos.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.TorneoRepository;

@Service
public class TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;

    public List<TorneoModel> obtenerTorneos() {
        return torneoRepository.findAll();
    }
 
    public Optional<TorneoModel> obtenerPorId(Long id) {
        return torneoRepository.findById(id);
    }

    public TorneoModel crear(TorneoModel torneo) {
        return torneoRepository.save(torneo);
    } 
      
    public void eliminar(Long id) {
        torneoRepository.deleteById(id);
    }

    public void actualizar(Long id, TorneoModel nuevosDatos) {
        TorneoModel existente = torneoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(nuevosDatos.getNombre());
            existente.setDescripcion(nuevosDatos.getDescripcion());
            existente.setFechaIni(nuevosDatos.getFechaIni());
            existente.setFechaFin(nuevosDatos.getFechaFin());
            existente.setPublicado(nuevosDatos.getPublicado());
            torneoRepository.save(existente);
        }
    } 
}
