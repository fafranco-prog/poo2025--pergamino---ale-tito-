package ar.edu.unnoba.poo2025.torneos.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.service.CompetenciaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competencias")
public class CompetenciaResource {

    @Autowired
    private CompetenciaService competenciaService;

    @GetMapping("/getCompetencias")
    public List<CompetenciaModel> getCompetencias() {
        return competenciaService.obtenerCompetencias();
    }

    @GetMapping("/getCompetenciaPorId/{id}")
    public Optional<CompetenciaModel> getCompetenciaPorId(@PathVariable Long id) {
        return competenciaService.obtenerPorId(id);
    }

    @PostMapping("/addCompetencia")
    public CompetenciaModel crearCompetencia(@RequestBody CompetenciaModel competencia) {
        return competenciaService.crear(competencia);
    }

    @DeleteMapping("/delCompetencia/{id}")
    public void eliminarCompetencia(@PathVariable Long id) {
        competenciaService.eliminar(id);
    }

    @PutMapping("/updateCompetencia/{id}")
    public void actualizarCompetencia(@PathVariable Long id, @RequestBody CompetenciaModel nuevosDatos) {
        competenciaService.actualizar(id, nuevosDatos);
    }
}
