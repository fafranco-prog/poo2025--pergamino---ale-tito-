package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;

import ar.edu.unnoba.poo2025.torneos.dto.AdminResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearAdminRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponse2DTO;
import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.AdministradorRepository;
import ar.edu.unnoba.poo2025.torneos.util.JwtTokenUtil;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;
 
@Service
public class AdministradorServiceImp implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //TP6
    @Autowired @Lazy
    private AuthenticationServiceImp authenticationService;
    @Autowired @Lazy 
    private AuthorizationServiceImp authorizationService;
    @Autowired
    private TorneoServiceImp torneoService;
      @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<AdminResponseDTO> obtenerAdministrador() {
        List<AdministradorModel> admins = administradorRepository.findAll();
        List<AdminResponseDTO> response = admins.stream()
                .map(t->modelMapper.map(t,AdminResponseDTO.class))
                .collect(Collectors.toList());
        return response;
    }
    @Override
    public Optional<AdministradorModel> obtenerPorId(Long id){
        return administradorRepository.findById(id);
    }
    @Override
    public void crear(CrearAdminRequestDTO administrador) throws Exception {
        if (administradorRepository.buscarPorEmail(administrador.getEmail()).isPresent()){
            throw new Exception("el email ya esta en uso");
        }
        AdministradorModel administradorModel = modelMapper.map(administrador, AdministradorModel.class);
        String hashedPassword = passwordEncoder.codificar(administradorModel.getContraseña());
        administradorModel.setContraseña(hashedPassword);
        
        administradorRepository.save(administradorModel);

    }

    @Override
    public void eliminar(Long id) {
        administradorRepository.deleteById(id);
    }
    @Override
    public void actualizar(Long id, AdministradorModel nuevosDatos) {
        AdministradorModel existente = administradorRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setEmail(nuevosDatos.getEmail());
            existente.setContraseña(nuevosDatos.getContraseña());
            administradorRepository.save(existente);
        }
    }

    @Override
    public AdministradorModel obtenerPorEmail(String email){
        return administradorRepository.buscarPorEmail(email).orElse(null);
    }
    
    //TP6
    @Override
    public String generarToken(AuthenticationRequestDTO authenticationRequestDTO)throws Exception{
        try {
            AdministradorModel adminModel = modelMapper
            .map(authenticationRequestDTO, AdministradorModel.class);
            return authenticationService.authenticateAdmin(adminModel);
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public void autorizar (String token) throws Exception{
        try {

            authorizationService.authorizeAdmin(token);    
        } catch (Exception e) {
            throw e;
        }  
    }
    @Override
    public List<TorneoResponse2DTO> getTorneosOrdenadosDesc(){
        List<TorneoModel> torneos = torneoService.getTorneosOrdenadosDesc();
        List<TorneoResponse2DTO> response = torneos.stream()
                    .map(t->modelMapper.map(t, TorneoResponse2DTO.class))
                    .collect(Collectors.toList());
        return response;
    }
    
    @Override
    public TorneoDetalleDTO getTorneoPorId(Long id)throws Exception{
        try{
            return torneoService.obtenerTorneoDetalleDTO(id);
        }
        catch (Exception e ){
            throw e; 
        } 
    }
    @Override
    public void crearTorneo(CrearTorneoDTO torneo) throws Exception{
        try{
            TorneoModel torneoModel = modelMapper.map(torneo, TorneoModel.class);
            torneoModel.setPublicado(false); 
            torneoService.crear(torneoModel);
        }
        catch (Exception e ){
            throw e;
        }
    }

}
 

