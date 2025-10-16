package ar.edu.unnoba.poo2025.torneos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.service.InscripcionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping("/getInscripciones")
    public List<InscripcionModel> getInscripciones() {
        return inscripcionService.obtenerInscripciones();
    }

    @GetMapping("/getInscripcionPorId/{id}")
    public Optional<InscripcionModel> getInscripcionPorId(@PathVariable Long id) {
        return inscripcionService.obtenerPorId(id);
    }

    @PostMapping("/addInscripcion")
    public InscripcionModel crearInscripcion(@RequestBody InscripcionModel inscripcion) {
        return inscripcionService.crear(inscripcion);
    }

    @DeleteMapping("/delInscripcion/{id}")
    public void eliminarInscripcion(@PathVariable Long id) {
        inscripcionService.eliminar(id);
    }
 
    @PutMapping("/updateInscripcion/{id}")
    public void actualizarInscripcion(@PathVariable Long id, @RequestBody InscripcionModel nuevosDatos) {
        inscripcionService.actualizar(id, nuevosDatos);
    }
}
