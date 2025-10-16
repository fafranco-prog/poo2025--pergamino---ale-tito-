package ar.edu.unnoba.poo2025.torneos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.service.ParticipanteService;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping("/getParticipantes")
    public List<ParticipanteModel> getParticipantes() {
        return participanteService.obtenerParticipantes();
    }

    @GetMapping("/getParPorId/{id}")
    public Optional<ParticipanteModel> getParticipantePorId(@PathVariable Long id) {
        return participanteService.obtenerPorId(id);
    }

    @PostMapping("/addParticipante")
    public ParticipanteModel crearParticipante(@RequestBody ParticipanteModel participante) {
        return participanteService.crear(participante);
    }

    @DeleteMapping("/delParticipante/{id}")
    public void eliminarParticipante(@PathVariable Long id) {
        participanteService.eliminar(id);
    }

    @PutMapping("/updateParticipante/{id}")
    public void actualizarParticipante(@PathVariable Long id, @RequestBody ParticipanteModel datosNuevos) {
        participanteService.actualizar(id, datosNuevos);
    }
}
