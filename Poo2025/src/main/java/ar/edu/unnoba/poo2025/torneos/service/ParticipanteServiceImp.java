package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.exception.DuplicateResourceException;
import ar.edu.unnoba.poo2025.torneos.exception.ResourceNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.repository.ParticipanteRepository;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;

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
    public ParticipanteModel obtenerPorId(Long id) {
        return participanteRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró un participante con el ID: " + id));
    }

    @Override
    public void crear(ParticipanteModel participante) {
        if (participanteRepo.buscarPorEmail(participante.getEmail()).isPresent()) {
            throw new DuplicateResourceException("El email ya está en uso.");
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

    @Override
    public ParticipanteModel obtenerPorEmail(String email) {
        return participanteRepo.buscarPorEmail(email).orElse(null);
    }
}
