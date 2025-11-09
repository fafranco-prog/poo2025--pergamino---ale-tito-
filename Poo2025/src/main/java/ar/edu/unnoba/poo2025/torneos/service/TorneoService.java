package ar.edu.unnoba.poo2025.torneos.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

@Service
public interface TorneoService {

    public List<TorneoModel> obtenerTorneos();
 
    public Optional<TorneoModel> obtenerPorId(Long id);

    public TorneoModel crear(TorneoModel torneo);
       
    public void eliminar(Long id);

    public void actualizar(Long id, TorneoModel nuevosDatos);

    public List<TorneoModel> findByPublishedTrue();
}
