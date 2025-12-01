package ar.edu.unnoba.poo2025.torneos.resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscripcionDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscripcionResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.ParticipanteRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoActivoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.service.ParticipanteService;

@RestController
public class ParticipanteResource {

    @Autowired
    private ParticipanteService participanteService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<Map<String, String>> authentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        ParticipanteModel participanteModel = modelMapper.map(authenticationRequestDTO, ParticipanteModel.class);
        String token = participanteService.authenticate(participanteModel);
        Map<String, String> response = Map.of("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/account")
    public ResponseEntity<Void> addParticipante(@RequestBody ParticipanteRequestDTO participanteDTO) {
        participanteDTO.validate();
        ParticipanteModel participanteModel = modelMapper.map(participanteDTO, ParticipanteModel.class);
        participanteService.crear(participanteModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/tournaments")
    public ResponseEntity<List<TorneoActivoResponseDTO>> getTorneosActivos(@RequestHeader("Authorization") String token) {
        participanteService.authorization(token);
        List<TorneoModel> torneos = participanteService.getTorneosActivos();
        List<TorneoActivoResponseDTO> response = torneos.stream()
                .map(t -> modelMapper.map(t, TorneoActivoResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/tournament/{id}")
    public ResponseEntity<TorneoDetalleDTO> getTorneoById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        participanteService.authorization(token);
        TorneoModel torneo = participanteService.getTorneoById(id);
        TorneoDetalleDTO response = modelMapper.map(torneo, TorneoDetalleDTO.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/tournament/{tournamentId}/competitions")
    public ResponseEntity<List<CompetenciaResponseDTO>> getCompetenciaByTorneoId(@RequestHeader("Authorization") String token, @PathVariable Long tournamentId) {
        participanteService.authorization(token);
        List<CompetenciaResponseDTO> competencias = participanteService.getCompetenciaByTorneoId(tournamentId);
        return new ResponseEntity<>(competencias, HttpStatus.OK);
    }

    @GetMapping(path = "/tournament/{tournamentId}/competitions/{id}")
    public ResponseEntity<CompetenciaResponseDTO> getCompetenciaById(@RequestHeader("Authorization") String token, @PathVariable Long tournamentId, @PathVariable Long id) {
        participanteService.authorization(token);
        CompetenciaModel competencia = participanteService.getCompetenciaById(tournamentId, id);
        CompetenciaResponseDTO response = modelMapper.map(competencia, CompetenciaResponseDTO.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/tournament/{tournamentId}/competitions/{id}/inscription")
    public ResponseEntity<Void> inscribirseEnCompetencia(@RequestHeader("Authorization") String token, @PathVariable Long tournamentId, @PathVariable Long id) {
        ParticipanteModel participante = participanteService.authorization(token);
        participanteService.inscribirseEnCompetencia(tournamentId, id, participante.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/inscriptions")
    public ResponseEntity<List<InscripcionResponseDTO>> getInscripciones(@RequestHeader("Authorization") String token) {
        participanteService.authorization(token);
        List<InscripcionResponseDTO> inscripciones = participanteService.getInscripciones();
        return new ResponseEntity<>(inscripciones, HttpStatus.OK);
    }

    @GetMapping(path = "/inscriptions/{id}")
    public ResponseEntity<InscripcionDetalleDTO> getInscripcionById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        participanteService.authorization(token);
        InscripcionDetalleDTO inscripcion = participanteService.getInscripcionDTOById(id);
        return new ResponseEntity<>(inscripcion, HttpStatus.OK);
    }
}
