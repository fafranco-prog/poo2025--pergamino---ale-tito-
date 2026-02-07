package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.ActualizarTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AdminResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaDetalleOutDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaPorTorneoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearAdminRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearCompetenciaDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponse2DTO;
import ar.edu.unnoba.poo2025.torneos.exception.DuplicateResourceException;
import ar.edu.unnoba.poo2025.torneos.exception.ResourceNotFoundException;
import ar.edu.unnoba.poo2025.torneos.exception.SelfDeletionException;
import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.AdministradorRepository;

@Service
public class AdministradorServiceImp implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    @Lazy
    private AuthenticationServiceImp authenticationService;
    @Autowired
    @Lazy
    private AuthorizationServiceImp authorizationService;
    @Autowired
    private TorneoService torneoService;
    @Autowired
    private CompetenciaService competenciaService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AdminResponseDTO> obtenerAdministrador() {
        List<AdministradorModel> admins = administradorRepository.findAll();
        List<AdminResponseDTO> response = admins.stream()
                .map(t -> modelMapper.map(t, AdminResponseDTO.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public AdministradorModel obtenerPorId(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un administrador con el ID: " + id));
    }

    //ARREGLADO
    @Override
    public void crear(CrearAdminRequestDTO administrador) {
        //las validaciones pertinen de nulos y demas se hacen de manera automatica, esta manera se esta probando en caso de no tener la resputa
        //deseada se volvera a la forma tradicional
        if (administradorRepository.buscarPorEmail(administrador.getEmail()).isPresent()) {
            throw new DuplicateResourceException("El email '" + administrador.getEmail() + "' ya está en uso.");
        }
        AdministradorModel administradorModel = modelMapper.map(administrador, AdministradorModel.class);
        String hashedPassword = authenticationService.encodePassword(administradorModel.getContraseña());
        administradorModel.setContraseña(hashedPassword);

        administradorRepository.save(administradorModel);

    }

    @Override
    public void eliminar(Long id, String token) {
        AdministradorModel adminAuth = autorizar(token);
        obtenerPorId(id);
        if (adminAuth.getId().equals(id)) {
            throw new SelfDeletionException("Un administrador no puede eliminarse a sí mismo.");
        }
        administradorRepository.deleteById(id);
    }

    // @Override
    // public void actualizar(Long id, AdministradorModel nuevosDatos) {
    //     AdministradorModel existente = obtenerPorId(id);
    //     existente.setEmail(nuevosDatos.getEmail());
    //     existente.setContraseña(nuevosDatos.getContraseña());
    //     administradorRepository.save(existente);
    // }
    @Override
    public AdministradorModel obtenerPorEmail(String email) {
        return administradorRepository.buscarPorEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un administrador con el email: " + email));
    }

    //TP6
    @Override
    public String generarToken(AuthenticationRequestDTO authenticationRequestDTO) {
        AdministradorModel adminModel = modelMapper.map(authenticationRequestDTO, AdministradorModel.class);
        return authenticationService.authenticateAdmin(adminModel);
    }

    @Override
    public AdministradorModel autorizar(String token) {
        AdministradorModel adminAuth = authorizationService.authorizeAdmin(token);
        return adminAuth;
    }

    @Override
    public List<TorneoResponse2DTO> getTorneosOrdenadosDesc() {
        List<TorneoModel> torneos = torneoService.getTorneosOrdenadosDesc();
        List<TorneoResponse2DTO> response = torneos.stream()
                .map(t -> modelMapper.map(t, TorneoResponse2DTO.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public TorneoDetalleDTO getTorneoPorId(Long id) {
        return torneoService.obtenerTorneoDetalleDTO(id);
    }

    //ARREGLADO
    @Override
    public void crearTorneo(CrearTorneoDTO torneo) {

        if (torneo.getFechaFin() != null && torneo.getFechaFin().isBefore(torneo.getFechaIni())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        TorneoModel torneoModel = modelMapper.map(torneo, TorneoModel.class);
        torneoModel.setPublicado(false);
        torneoService.crear(torneoModel);
    }

    @Override
    public void actualizarTorneo(Long id, ActualizarTorneoDTO torneo) {
        torneoService.actualizar(id, torneo);
    }

    @Override
    public void eliminarTorneo(Long id) {
        torneoService.eliminar(id);
    }

    //AREGLADO
    @Override
    public List<CompetenciaPorTorneoResponseDTO> getCompetenciasPoridTorneo(Long id) {
        return competenciaService.obtenerCompetenciasDeTorneo(id);
    }

    @Override
    public CompetenciaDetalleOutDTO getEstadisticasCompetencia(Long id, Long idTorneo) {
        return competenciaService.obtenerInscripcionesTotalesConMontos(id, idTorneo);
    }

    @Override
    public void crearCompetenciaConTorneoAsignado(CrearCompetenciaDTO dto, Long idTorneo) {
        competenciaService.crearCompetenciaConTorneo(dto, idTorneo);
    }

    //REUTILIZE EL DTO POR QUE LOS CAMPOS SON IGUALES, PARA QUE QUEDE MAS EXPLICITO SE TENDRIA QUE HACER UN DTO 
    //ESPECIFICO PARA ESTO
    @Override
    public void actualizarCompetencia(CrearCompetenciaDTO dto, Long id, Long tournamentId) {
        competenciaService.actualizar(id, dto, tournamentId);
    }

    @Override
    public void eliminarCompetencia(Long id, Long idTorneo) {
        competenciaService.eliminar(id, idTorneo);
    }

    @Override
    public void cambiarEstadoAPublicado(Long idTorneo) {
        torneoService.cambiarEstadoAPublicado(idTorneo);
    }

    @Override
    public List<InscripcionModel> inscripcionesDeCompetencia(Long idCompetencia, Long idTorneo) {
        return competenciaService.inscripcionesCompetencia(idCompetencia, idTorneo);
    }
}
