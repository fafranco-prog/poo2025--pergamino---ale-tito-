package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;

import ar.edu.unnoba.poo2025.torneos.dto.ActualizarTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

public interface TorneoService {

    public List<TorneoModel> obtenerTorneos();

    public TorneoModel obtenerPorId(Long id);

    public void crear(TorneoModel torneo);

    public void eliminar(Long id);

    public void actualizar(Long id, ActualizarTorneoDTO nuevosDatos);

    public List<TorneoModel> getTorneosOrdenadosDesc();

    public TorneoDetalleDTO obtenerTorneoDetalleDTO(Long torneoId);

    public void cambiarEstadoAPublicado(Long idTorneo);

    public TorneoModel obtenerTorneoActivoParaInscripcion(Long id);

    public List<TorneoModel> getTorneosActivos();

    public void validarTorneoNoPublicado(TorneoModel torneo);

}
