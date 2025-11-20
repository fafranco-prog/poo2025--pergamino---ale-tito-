package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.AdministradorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.servlet.function.EntityResponse;

import ar.edu.unnoba.poo2025.torneos.dto.AdminResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearAdminRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponse2DTO;


@Service
public interface AdministradorService {

    public List<AdminResponseDTO> obtenerAdministrador();

    public Optional<AdministradorModel> obtenerPorId(Long id);

    public void crear(CrearAdminRequestDTO administrador)throws Exception;
 
    public void eliminar(Long id);

    public void actualizar(Long id, AdministradorModel nuevosDatos);

    public AdministradorModel obtenerPorEmail(String email);

    //fijarse si sirve por que momentaneamente no sirve por que discrimina solo
    //public String getTipoUsuario (Long id);

    public String generarToken(AuthenticationRequestDTO authenticationRequestDTO)throws Exception;
    public void autorizar (String token) throws Exception;  
    public List<TorneoResponse2DTO> getTorneosOrdenadosDesc();
    public TorneoDetalleDTO getTorneoPorId(Long id)throws Exception; 
    public void crearTorneo(CrearTorneoDTO torneo) throws Exception;
    
}
 