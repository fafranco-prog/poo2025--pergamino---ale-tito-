package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.repository.ParticipanteRepository;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteServiceImp implements ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<ParticipanteModel> obtenerParticipantes() {
        return participanteRepo.findAll();
    }

    @Override
    public Optional<ParticipanteModel> obtenerPorId(Long id) {
        return participanteRepo.findById(id);
    }

    @Override
    public void crear(ParticipanteModel participante) throws Exception {
        if (participanteRepo.buscarPorEmail(participante.getEmail()).isPresent()) {
            throw new Exception("El email ya está en uso.");
        }

        String hashedPassword = passwordEncoder.codificar(participante.getContraseña());
        participante.setContraseña(hashedPassword);

        participanteRepo.save(participante);
    }

    @Override
    public void eliminar(Long id) {
        participanteRepo.deleteById(id);
    }

    @Override
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
