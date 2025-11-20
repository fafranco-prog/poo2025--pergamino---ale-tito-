package ar.edu.unnoba.poo2025.torneos.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.function.EntityResponse;

import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.service.AuthorizationServiceImp;
import ar.edu.unnoba.poo2025.torneos.service.TorneoServiceImp;

@RestController
@RequestMapping("/torneos")
public class TorneoResource {

    @Autowired
    private TorneoServiceImp torneoService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthorizationServiceImp authorizationService;


    @GetMapping("/getTorneos")
    public ResponseEntity<?> getTorneos(@RequestHeader("Authorization") String token) {

        try {        
            authorizationService.authorize(token);
 
            List<TorneoModel> torneos = torneoService.findByPublishedTrue();
           
            List<TorneoResponseDTO> response = torneos.stream()
                    .map(t->modelMapper.map(t, TorneoResponseDTO.class))
                    .collect(Collectors.toList());
          
            return ResponseEntity.ok(response);         
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no autorizado: token invalido o expirado");
        }
    } 
              
    @GetMapping("/getTorneoPorId/{id}")
    public Optional<TorneoModel> getTorneoPorId(@PathVariable Long id) {
        return torneoService.obtenerPorId(id);
    }

    @PostMapping("/addTorneo")
    public ResponseEntity<String> crearTorneo(@RequestBody TorneoModel torneo) {
        try {
            torneoService.crear(torneo);
            return ResponseEntity.status(HttpStatus.CREATED)
                             .body("Torneo creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Ha ocurrido un error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delTorneo/{id}")
    public void eliminarTorneo(@PathVariable Long id) {
        torneoService.eliminar(id);
    }

    @PutMapping("/updateTorneo/{id}")
    public void actualizarTorneo(@PathVariable Long id, @RequestBody TorneoModel nuevosDatos) {
        torneoService.actualizar(id, nuevosDatos);
    }
}
  