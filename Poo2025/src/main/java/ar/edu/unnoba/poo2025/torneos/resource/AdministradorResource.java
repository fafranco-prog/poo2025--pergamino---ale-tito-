package ar.edu.unnoba.poo2025.torneos.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unnoba.poo2025.torneos.dto.AdminResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearAdminRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponse2DTO;
import ar.edu.unnoba.poo2025.torneos.service.AdministradorService;

@RestController
@RequestMapping("/admin")
public class AdministradorResource {

    @Autowired
    private AdministradorService adminService;

    @GetMapping("/cuentas")
    public ResponseEntity<List<AdminResponseDTO>> getAdministradores(@RequestHeader("Authorization") String token) {
        adminService.autorizar(token);
        List<AdminResponseDTO> response = adminService.obtenerAdministrador();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cuentas")
    public ResponseEntity<Void> addAdmin(@RequestHeader("Authorization") String token, @RequestBody CrearAdminRequestDTO adminDTO) {
        adminService.autorizar(token);
        adminService.crear(adminDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<Void> delAdmin(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        adminService.autorizar(token);
        adminService.eliminar(id, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/torneos")
    public ResponseEntity<List<TorneoResponse2DTO>> getTorneos(@RequestHeader("Authorization") String token) {
        adminService.autorizar(token);
        List<TorneoResponse2DTO> response = adminService.getTorneosOrdenadosDesc();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/torneos/{id}")
    public ResponseEntity<TorneoDetalleDTO> getTorneoPorId(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        adminService.autorizar(token);
        TorneoDetalleDTO response = adminService.getTorneoPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/torneos")
    public ResponseEntity<Void> crearTorneo(@RequestHeader("Authorization") String token, @RequestBody CrearTorneoDTO torneoDTO) {
        adminService.autorizar(token);
        adminService.crearTorneo(torneoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<Map<String, String>> authentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        String tokenValue = adminService.generarToken(authenticationRequestDTO);
        Map<String, String> response = Map.of("token", tokenValue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
