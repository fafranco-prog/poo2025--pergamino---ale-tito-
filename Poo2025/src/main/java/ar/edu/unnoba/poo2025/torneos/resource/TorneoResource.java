package ar.edu.unnoba.poo2025.torneos.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.service.TorneoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/torneos")
public class TorneoResource {

    @Autowired
    private TorneoService torneoService;

    @GetMapping("/getTorneos")
    public List<TorneoModel> getTorneos() {
        return torneoService.obtenerTorneos();
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
