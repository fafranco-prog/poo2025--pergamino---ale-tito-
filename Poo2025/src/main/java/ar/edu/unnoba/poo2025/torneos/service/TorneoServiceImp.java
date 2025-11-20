package ar.edu.unnoba.poo2025.torneos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
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


    //TP6 
    @Override
    public List<TorneoModel> getTorneosOrdenadosDesc(){
        List<TorneoModel> aux = new ArrayList<>(obtenerTorneos());
        aux.sort((t1,t2) -> t2.getFechaIni().compareTo(t1.getFechaIni()));
        return aux;     
    }
    @Override
    public TorneoDetalleDTO obtenerTorneoDetalleDTO(Long torneoId)throws Exception{
        TorneoModel torneo = torneoRepository.findById(torneoId)
        .orElseThrow(()-> new Exception("torneo no encontrado"));

        int totalInscripciones = 0;
        double montoTotal = 0;
        if(torneo.getCompetencias()!=null){
            for(CompetenciaModel c : torneo.getCompetencias()){
                if(c.getInscripciones()!=null){
                    totalInscripciones += c.getInscripciones().size();
                    for(InscripcionModel i : c.getInscripciones()){
                        montoTotal+=i.getPrecioPagado();
                        }
                    }
                }  
            }
        TorneoDetalleDTO dto = new TorneoDetalleDTO();
        dto.setId(torneo.getId());
        dto.setNombre(torneo.getNombre());
        dto.setPublicado(torneo.getPublicado());
        dto.setTotalInscripciones(totalInscripciones);
        dto.setMontoTotal(montoTotal);
        
        return dto;
    } 

    @Override
    public void crear( TorneoModel torneo) throws Exception{
        if(torneoRepository.buscarPorNombre(torneo.getNombre()).isPresent()){
            throw new Exception("ya existe el torneo con ese nombre");
        }        
        torneoRepository.save(torneo);    
    }   
}
