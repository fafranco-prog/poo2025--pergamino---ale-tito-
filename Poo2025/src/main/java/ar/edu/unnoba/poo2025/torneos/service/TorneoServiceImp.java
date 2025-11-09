package ar.edu.unnoba.poo2025.torneos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.TorneoRepository;
 
@Service
public class TorneoServiceImp implements TorneoService {
    
    @Autowired
    private TorneoRepository torneoRepository;

    @Override
    public List<TorneoModel> obtenerTorneos() {
        return torneoRepository.findAll();
    }

    @Override
    public List<TorneoModel> findByPublishedTrue(){
        java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
        List<TorneoModel> aux = new ArrayList<>();
        for (TorneoModel torneos : obtenerTorneos()){
            if(Boolean.TRUE.equals(torneos.getPublicado()) 
            && !torneos.getFechaIni().after(hoy) 
            && (torneos.getFechaFin()==null || torneos.getFechaFin().before(hoy))){
                aux.add(torneos); 
            } 
        }
        return aux;
    }
       
    @Override
    public Optional<TorneoModel> obtenerPorId(Long id) {
        return torneoRepository.findById(id);
    }
    @Override
    public TorneoModel crear(TorneoModel torneo) {
        return torneoRepository.save(torneo);
    } 
    @Override
    public void eliminar(Long id) {
        torneoRepository.deleteById(id);
    }
    @Override
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
