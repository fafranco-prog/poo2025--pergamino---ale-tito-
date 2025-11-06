package ar.edu.unnoba.poo2025.torneos.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.service.TorneoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.service.AuthorizationService;

@RestController
@RequestMapping("/torneos")
public class TorneoResource {

    @Autowired
    private TorneoService torneoService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthorizationService authorizationService;


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
    public TorneoModel crearTorneo(@RequestBody TorneoModel torneo) {
        return torneoService.crear(torneo);
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
  