package ar.edu.unnoba.poo2025.torneos.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.service.AdministradorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdministradorResource {

    @Autowired
    private AdministradorService adminService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AdminResponseDTO>> getAdministradores(@RequestHeader("Authorization") String token) {
        adminService.autorizar(token);
        List<AdminResponseDTO> response = adminService.obtenerAdministrador();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Void> addAdmin(@RequestHeader("Authorization") String token, @Valid @RequestBody CrearAdminRequestDTO adminDTO) {
        adminService.autorizar(token);
        adminService.crear(adminDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> delAdmin(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        adminService.autorizar(token);
        adminService.eliminar(id, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tournaments")
    public ResponseEntity<List<TorneoResponse2DTO>> getTorneos(@RequestHeader("Authorization") String token) {
        adminService.autorizar(token);
        List<TorneoResponse2DTO> response = adminService.getTorneosOrdenadosDesc();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/tournaments/{id}")
    public ResponseEntity<TorneoDetalleDTO> getTorneoPorId(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        adminService.autorizar(token);
        TorneoDetalleDTO response = adminService.getTorneoPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/tournaments")
    public ResponseEntity<Void> crearTorneo(@RequestHeader("Authorization") String token, @Valid @RequestBody CrearTorneoDTO torneoDTO) {
        adminService.autorizar(token);
        adminService.crearTorneo(torneoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //SE MODIFICARON LOS TIPOS DE FECHA POR ERROR DE ACTUALIZACION DE TUPLAS EN LA BASE DE DATOS

    @PutMapping("/tournaments/{id}")
    public ResponseEntity<Void> actualizarTorneo(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody ActualizarTorneoDTO torneo) {
        adminService.autorizar(token);
        adminService.actualizarTorneo(id, torneo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/tournaments/{id}")
    public ResponseEntity<Void> eliminarTorneo(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        adminService.autorizar(token);
        adminService.eliminarTorneo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/tournaments/{id}/competitions")
    public ResponseEntity<List<CompetenciaPorTorneoResponseDTO>> obtenerCompetencias(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        adminService.autorizar(token);
        List<CompetenciaPorTorneoResponseDTO> compe = adminService.getCompetenciasPoridTorneo(id);
        return new ResponseEntity<>(compe, HttpStatus.OK);
    }

    @GetMapping("/tournaments/{tournamentId}/competitions/{id}")
    public ResponseEntity<CompetenciaDetalleOutDTO> obtenerEstadisticas(@RequestHeader("Authorization") String token, @PathVariable Long tournamentId, @PathVariable Long id) {
        adminService.autorizar(token);
        CompetenciaDetalleOutDTO compe = adminService.getEstadisticasCompetencia(id, tournamentId);
        return new ResponseEntity<>(compe, HttpStatus.OK);
    }

    @PostMapping("/tournaments/{tournamentId}")
    public ResponseEntity<Void> crearCompetenciaConTorneo(@RequestHeader("Authorization") String token, @PathVariable Long tournamentId, @RequestBody CrearCompetenciaDTO dto) {
        adminService.autorizar(token);
        adminService.crearCompetenciaConTorneoAsignado(dto, tournamentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    En el archivo figura el siguiente endpoint
    
    Change Tournament competition details 
    Actualiza el nombre y el género de una canción. Solo el usuario que creó la canción puede hacerlo. 
    PUT /admin/tournaments/:id 

    Como no coincidia con lo pedido ya que hablaba de una cancion, se asumio que era para actualizar una competencia (ya existe un endpoint que actualiza el torneo como tal)
     */
    @PutMapping("/tournaments/{idCompetencia}/competitions")
    public ResponseEntity<Void> actualizarCompetencia(@RequestHeader("Authorization") String token, @PathVariable Long idCompetencia, @RequestBody CrearCompetenciaDTO dto) {
        adminService.autorizar(token);
        adminService.actualizarCompetencia(dto, idCompetencia);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/tournaments/{tournamentId}/competitions/{id}")
    public ResponseEntity<Void> eliminarCompetencia(@RequestHeader("Authorization") String token, @PathVariable Long id, @PathVariable Long tournamentId) {
        adminService.autorizar(token);
        adminService.eliminarCompetencia(id, tournamentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/tournaments/{tournamentId}/published")
    public ResponseEntity<Void> torneoPublicado(@RequestHeader("Authorization") String token, @PathVariable Long tournamentId) {
        adminService.autorizar(token);
        adminService.cambiarEstadoAPublicado(tournamentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/tournaments/{tournamentId}/competitions/{id}/inscriptions")
    public ResponseEntity<List<InscripcionModel>> torneoPublicado(@RequestHeader("Authorization") String token, @PathVariable Long tournamentId, @PathVariable Long id) {
        adminService.autorizar(token);
        List<InscripcionModel> inscripciones = adminService.inscripcionesDeCompetencia(id, tournamentId);
        return new ResponseEntity<>(inscripciones, HttpStatus.OK);
    }

    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<Map<String, String>> authentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        String tokenValue = adminService.generarToken(authenticationRequestDTO);
        Map<String, String> response = Map.of("token", tokenValue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
