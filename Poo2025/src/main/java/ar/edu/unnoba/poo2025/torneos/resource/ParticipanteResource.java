package ar.edu.unnoba.poo2025.torneos.resource;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.dto.CrearParticipanteRequestDTO;
import ar.edu.unnoba.poo2025.torneos.service.AuthenticationServiceImp;
import ar.edu.unnoba.poo2025.torneos.service.ParticipanteServiceImp;

@RestController
@RequestMapping("/participante")
public class ParticipanteResource {

    @Autowired
    private ParticipanteServiceImp participanteService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationServiceImp authenticationService;

    @GetMapping("/getParticipantes")
    public List<ParticipanteModel> getParticipantes() {
        return participanteService.obtenerParticipantes();
    }

    @GetMapping("/getParPorId/{id}")
    public Optional<ParticipanteModel> getParticipantePorId(@PathVariable Long id) {
        return participanteService.obtenerPorId(id);
    }

    @DeleteMapping("/delParticipante/{id}")
    public void eliminarParticipante(@PathVariable Long id) {
        participanteService.eliminar(id);
    }

    @PutMapping("/updateParticipante/{id}")
    public void actualizarParticipante(@PathVariable Long id, @RequestBody ParticipanteModel datosNuevos) {
        participanteService.actualizar(id, datosNuevos);
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> crearParticipante(@RequestBody CrearParticipanteRequestDTO participanteDTO) {
        try {
            ParticipanteModel participanteModel = modelMapper.map(participanteDTO, ParticipanteModel.class);
            participanteService.crear(participanteModel);
            return new ResponseEntity<>(participanteModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            ParticipanteModel participanteModel = modelMapper.map(authenticationRequestDTO, ParticipanteModel.class);
            String token = authenticationService.authenticate(participanteModel);
            Map<String, String> response = Collections.singletonMap("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
