package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscripcionResponseDTO;
import ar.edu.unnoba.poo2025.torneos.exception.DuplicateResourceException;
import ar.edu.unnoba.poo2025.torneos.exception.NotAllowedException;
import ar.edu.unnoba.poo2025.torneos.exception.ResourceNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.ParticipanteRepository;

@Service
public class ParticipanteServiceImp implements ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    @Lazy
    private AuthenticationService authenticationService;
    @Autowired
    @Lazy
    private AuthorizationService authorizationService;
    @Autowired
    private TorneoService torneoService;
    @Autowired
    private InscripcionService inscripcionService;
    @Autowired
    private CompetenciaService competenciaService;
    @Autowired
    private ModelMapper modelMapper;

    // @Override
    // public List<ParticipanteModel> obtenerParticipantes() {
    //     return participanteRepository.findAll();
    // }
    @Override
    public ParticipanteModel obtenerPorId(Long id) {
        return participanteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró un participante con el ID: " + id));
    }

    @Override
    public void crear(ParticipanteModel participanteModel) {
        if (participanteRepository.buscarPorEmail(participanteModel.getEmail()).isPresent()) {
            throw new DuplicateResourceException("El email '" + participanteModel.getEmail() + "' ya está en uso.");
        }
        if (participanteRepository.findByTipoDniAndNumeroDni(participanteModel.getTipoDni(), participanteModel.getNumeroDni()).isPresent()) {
            throw new DuplicateResourceException("El DNI '" + participanteModel.getNumeroDni() + "' ya está en uso.");
        }

        try {
            String hashedPassword = authenticationService.encodePassword(participanteModel.getContraseña());
            participanteModel.setContraseña(hashedPassword);
            participanteRepository.save(participanteModel);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("El email '" + participanteModel.getEmail() + "' ya está en uso."); // Posiblemente añadir que como admin?
        }
    }

    // @Override
    // public void eliminar(Long id) {
    //     participanteRepository.deleteById(id);
    // }
    // @Override
    // public void actualizar(Long id, ParticipanteModel nuevosDatos) {
    //     ParticipanteModel existente = obtenerPorId(id);
    //     existente.setNombre(nuevosDatos.getNombre());
    //     existente.setApellido(nuevosDatos.getApellido());
    //     existente.setTipoDni(nuevosDatos.getTipoDni());
    //     existente.setNumeroDni(nuevosDatos.getNumeroDni());
    //     existente.setEmail(nuevosDatos.getEmail());
    //     existente.setContraseña(nuevosDatos.getContraseña());
    //     participanteRepository.save(existente);
    // }
    @Override
    public ParticipanteModel obtenerPorEmail(String email) {
        return participanteRepository.buscarPorEmail(email).orElseThrow(() -> new ResourceNotFoundException("No se encontró un participante con el email: " + email));
    }

    @Override
    public String authenticate(ParticipanteModel participante) {
        String token = authenticationService.authenticate(participante);
        return token;
    }

    @Override
    public List<TorneoModel> getTorneosActivos() {
        return torneoService.getTorneosActivos();
    }

    @Override
    public ParticipanteModel authorization(String token) {
        ParticipanteModel participante = authorizationService.authorize(token);
        return participante;
    }

    @Override
    public TorneoModel getTorneoById(Long id) {
        TorneoModel torneo = torneoService.obtenerPorId(id);
        return torneo;
    }

    @Override
    public List<CompetenciaResponseDTO> getCompetenciaByTorneoId(Long tournamentId) {
        TorneoModel torneo = torneoService.obtenerPorId(tournamentId);
        List<CompetenciaModel> competencias = torneo.getCompetencias();
        List<CompetenciaResponseDTO> response = competencias.stream()
                .map(c -> modelMapper.map(c, CompetenciaResponseDTO.class))
                .collect(Collectors.toList());
        return response;
    }

/*   @Override
    public CompetenciaModel getCompetenciaById(Long tournamentId, Long id) {
        TorneoModel torneo = torneoService.obtenerPorId(tournamentId);
        return torneo.getCompetencias().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("La competencia con ID " + id + " no fue encontrada o no pertenece al torneo con ID " + tournamentId));
    }*/

    @Override
    public CompetenciaModel getCompetenciaById(Long tournamentId, Long id) {
        torneoService.obtenerPorId(tournamentId);
        
        CompetenciaModel competencia = competenciaService.obtenerPorId(id);
        
        if (!competencia.getTorneo().getId().equals(tournamentId)) {
            throw new ResourceNotFoundException("La competencia con ID " + id + 
                " no pertenece al torneo con ID " + tournamentId);
        }
        
        return competencia;
    }

    @Override
    public void inscribirseEnCompetencia(Long tournamentId, Long competenciaId, Long participanteId) {

        TorneoModel torneo = torneoService.obtenerTorneoActivoParaInscripcion(tournamentId);

        CompetenciaModel competencia = competenciaService.obtenerCompetenciaDeTorneo(torneo, competenciaId);
        competenciaService.validarInscripcion(competencia, participanteId);

        inscripcionService.registrarInscripcion(competencia, participanteId);
    }

    @Override
    public List<InscripcionResponseDTO> getInscripciones() {
        return inscripcionService.obtenerInscripciones().stream()
                .map(i -> modelMapper.map(i, InscripcionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public InscripcionModel getInscripcionById(ParticipanteModel participante, Long id) {
        InscripcionModel inscripcion = inscripcionService.obtenerPorId(id);
        if (!inscripcion.getParticipante().getId().equals(participante.getId())) {
            throw new NotAllowedException("No tiene permiso para acceder a esta inscripción.");
        }
        return inscripcion;
    }
}
