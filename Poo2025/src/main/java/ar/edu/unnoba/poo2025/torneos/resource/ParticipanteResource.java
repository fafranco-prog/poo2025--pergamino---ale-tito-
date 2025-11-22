package ar.edu.unnoba.poo2025.torneos.resource;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearParticipanteRequestDTO;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.service.AuthenticationService;
import ar.edu.unnoba.poo2025.torneos.service.ParticipanteService;

@RestController
@RequestMapping("/participante")
public class ParticipanteResource {

    @Autowired
    private ParticipanteService participanteService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/getParticipantes")
    public ResponseEntity<List<ParticipanteModel>> getParticipantes() {
        List<ParticipanteModel> response = participanteService.obtenerParticipantes();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getParPorId/{id}")
    public ResponseEntity<ParticipanteModel> getParticipantePorId(@PathVariable Long id) {
        ParticipanteModel response = participanteService.obtenerPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delParticipante/{id}")
    public ResponseEntity<Void> eliminarParticipante(@PathVariable Long id) {
        participanteService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateParticipante/{id}")
    public ResponseEntity<Void> actualizarParticipante(@PathVariable Long id, @RequestBody ParticipanteModel datosNuevos) {
        participanteService.actualizar(id, datosNuevos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Void> crearParticipante(@RequestBody CrearParticipanteRequestDTO participanteDTO) {
        ParticipanteModel participanteModel = modelMapper.map(participanteDTO, ParticipanteModel.class);
        participanteService.crear(participanteModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<Map<String, String>> authentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        ParticipanteModel participanteModel = modelMapper.map(authenticationRequestDTO, ParticipanteModel.class);
        String tokenValue = authenticationService.authenticate(participanteModel);
        Map<String, String> response = Map.of("token", tokenValue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
