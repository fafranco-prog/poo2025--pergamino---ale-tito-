package ar.edu.unnoba.poo2025.torneos.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.repository.ParticipanteRepository;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepo;

    public List<ParticipanteModel> obtenerParticipantes() {
        return participanteRepo.findAll();
    }

    public Optional<ParticipanteModel> obtenerPorId(Long id) {
        return participanteRepo.findById(id);
    }

    public ParticipanteModel crear(ParticipanteModel participante) {
        return participanteRepo.save(participante);
    }
 
    public void eliminar(Long id) {
        participanteRepo.deleteById(id);
    }

    public void actualizar(Long id, ParticipanteModel nuevosDatos) {
        ParticipanteModel existente = participanteRepo.findById(id).orElse(null);
        if (existente != null) {
    
            existente.setNombre(nuevosDatos.getNombre());
            existente.setApellido(nuevosDatos.getApellido());
            existente.setTipoDni(nuevosDatos.getTipoDni());
            existente.setNumeroDni(nuevosDatos.getNumeroDni());
            existente.setEmail(nuevosDatos.getEmail());
            existente.setContraseña(nuevosDatos.getContraseña());

            participanteRepo.save(existente);
        }
    }
}
