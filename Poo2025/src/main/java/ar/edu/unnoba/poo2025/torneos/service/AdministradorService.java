package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.AdminResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearAdminRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponse2DTO;
import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;

@Service
public interface AdministradorService {

    public List<AdminResponseDTO> obtenerAdministrador();

    public AdministradorModel obtenerPorId(Long id);

    public void crear(CrearAdminRequestDTO administrador);

    public void eliminar(Long id, String token);

    public void actualizar(Long id, AdministradorModel nuevosDatos);

    public AdministradorModel obtenerPorEmail(String email);

    public String generarToken(AuthenticationRequestDTO authenticationRequestDTO);

    public AdministradorModel autorizar(String token);

    public List<TorneoResponse2DTO> getTorneosOrdenadosDesc();

    public TorneoDetalleDTO getTorneoPorId(Long id);

    public void crearTorneo(CrearTorneoDTO torneo);

}
